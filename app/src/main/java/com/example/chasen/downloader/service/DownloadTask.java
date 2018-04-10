package com.example.chasen.downloader.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.chasen.downloader.db.FileInfoDaoImpl;
import com.example.chasen.downloader.db.IFileInfoDao;
import com.example.chasen.downloader.db.IThreadInfoDao;
import com.example.chasen.downloader.db.ThreadInfoImpl;
import com.example.chasen.downloader.model.FileInfo;
import com.example.chasen.downloader.model.ThreadInfo;
import com.example.chasen.downloader.util.CloseUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chasen on 18-3-29.
 * <p>
 * 下载任务类
 */

public class DownloadTask {

    // 线程数量数
    public static final int THREAD_COUNT = 3;

    public static ExecutorService mExecutorService = Executors.newCachedThreadPool();
    // 是否暂停下载
    public boolean isPause = false;
    // 记录下载进度
    private int mFinished = 0;

    // 下载
    private List<DownloadThread> mDownloadThreadList;
    private IThreadInfoDao mThreadDao;
    private IFileInfoDao mFileDao;
    private Context mContext;
    private FileInfo mFileInfo;


    public DownloadTask(Context context, FileInfo fileInfo) {
        this.mContext = context;
        this.mFileInfo = fileInfo;
        mThreadDao = ThreadInfoImpl.getInstance(mContext);
        mFileDao = FileInfoDaoImpl.getInstance(mContext);
    }

    // 下载任务的执行触发
    public void download() {
        List<ThreadInfo> threadInfoList = mThreadDao.getThreads(mFileInfo.getUrl());
        if (threadInfoList.size() == 0) {
            int length = mFileInfo.getLength() / THREAD_COUNT;
            for (int i = 0; i < THREAD_COUNT; i++) {
                ThreadInfo threadInfo = new ThreadInfo(i, mFileInfo.getUrl(), i * length, (i + 1) * length-1, 0);
                // 可能除不尽，所以最后一个线程的结束位应该设为线程长度
                if (i == THREAD_COUNT - 1) {
                    threadInfo.setEnd(mFileInfo.getLength());
                }
                // 添加到数据库中和集合中
                mThreadDao.add(threadInfo);
                threadInfoList.add(threadInfo);
            }
        }
        mDownloadThreadList = new ArrayList<>();
        for (ThreadInfo threadInfo : threadInfoList) {
            DownloadThread downloadThread = new DownloadThread(threadInfo);
            mExecutorService.execute(downloadThread);
            mDownloadThreadList.add(downloadThread);
        }

    }

    private synchronized void checkAllFinished() {
        boolean allFinished = true;
        for (DownloadThread thread : mDownloadThreadList) {
            if (!thread.isFinished) {
                allFinished = false;
                break;
            }
        }
        if (allFinished) {
            mThreadDao.delete(mFileInfo.getUrl());
            // 更新文件信息
            mFileDao.update(mFileInfo.getId(), mFileInfo.getFinished(), true);
            // 通知下载完成
            Log.e("TAG", mFileInfo.toString());
            EventBus.getDefault().post(mFileInfo);
        }
    }

    /**
     * 多线程下载，真正的下载在这里执行
     */
    class DownloadThread extends Thread {

        private ThreadInfo threadInfo;
        // 线程是否执行结束
        public boolean isFinished = false;

        public DownloadThread(ThreadInfo threadInfo) {
            this.threadInfo = threadInfo;
        }

        @Override
        public void run() {
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            InputStream in = null;

            try {
                URL url = new URL(threadInfo.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setReadTimeout(3000);
                conn.setConnectTimeout(3000);
                // 设置下载范围
                int start = threadInfo.getStart() + threadInfo.getFinished();
                conn.setRequestProperty("Range", "bytes=" + start + "-" + threadInfo.getEnd());
                // 设置文件写入位置
                File file = new File(DownloadService.PATH, mFileInfo.getName());
                raf = new RandomAccessFile(file, "rwd");
                raf.seek(start);
                // 累加整个文件的下载进度
                mFinished += threadInfo.getFinished();
                // 请求成功
                if (conn.getResponseCode() == 206) {
                    int length = -1;
                    byte[] buff = new byte[1024 * 4];
                    in = conn.getInputStream();
                    long speed = 0;
                    // 用于计算网速
                    int finished = mFinished;
                    long time = System.currentTimeMillis();
                    while ((length = in.read(buff)) != -1) {
                        raf.write(buff, 0, length);
                        // 累加整个文件的下载进度和线程的下载进度
                        mFinished += length;
                        threadInfo.setFinished(threadInfo.getFinished() + length);
                        mFileInfo.setFinished(mFinished);

                        // 设置网速并通知更新 更新下载进度
                        if (System.currentTimeMillis() - time >= 2000) {
                            speed = (mFinished - finished) / ((System.currentTimeMillis() - time) / 1000);
                            finished = mFinished;
                            time = System.currentTimeMillis();
                            // 通知更新下载进度和下载速度
                            mFileInfo.setSpeed(speed);
                            mFileInfo.setDownloading(true);
                            EventBus.getDefault().post(mFileInfo);
                        }

                        // 暂停的时候更新数据库的信息并暂停下载
                        if (isPause) {
                            mFileDao.update(mFileInfo.getId(), mFileInfo.getFinished(), false);
                            mThreadDao.updateThreadInfo(threadInfo.getUrl(), threadInfo.getId(), threadInfo.getFinished());
                            mFileInfo.setDownloading(false);
                            EventBus.getDefault().post(mFileInfo);
                            return;
                        }
                    }
                    // 到这里的话，说明循环执行完毕，该线程下载的部分下载好了
                    isFinished = true;
                    // 检查是该文件下载的全部线程执行完毕
                    checkAllFinished();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
                CloseUtil.close(in);
                CloseUtil.close(raf);
            }
        }
    }

}
