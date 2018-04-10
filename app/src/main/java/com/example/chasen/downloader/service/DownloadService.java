package com.example.chasen.downloader.service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import com.example.chasen.downloader.db.FileInfoDaoImpl;
import com.example.chasen.downloader.db.IFileInfoDao;
import com.example.chasen.downloader.model.FileInfo;
import com.example.chasen.downloader.util.CloseUtil;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by chasen on 18-3-29.
 * 执行下载的服务，实现后台下载
 */

public class DownloadService extends Service {
    // 日志打印
    private static final String TAG = "DownloadService";
    // 开始下载、暂停下载标志
    public static final String ACTION_START = "ACTION_START";
    public static final String ACTION_PAUSE = "ACTION_PAUSE";
    // 文件保存路径
    public static final String PATH = Environment.getExternalStorageDirectory() + "/downloader";
    // Handler接受初始化消息标识
    private static final int MSG_INIT = 1;

    private IFileInfoDao mFileDao = FileInfoDaoImpl.getInstance(this);
    private SparseArray<DownloadTask> mTasks = new SparseArray<>();

    private FileInfo mFileInfo = null;
    private InitThread mInitThread = null;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_INIT:
                    FileInfo fileInfo = (FileInfo) msg.obj;
                    // 启动下载任务,并添加到下载任务集合中
                    DownloadTask downloadTask = new DownloadTask(DownloadService.this, fileInfo);
                    downloadTask.download();
                    mTasks.put(fileInfo.getId(), downloadTask);
                    break;
                default:
                    break;

            }
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mFileInfo = intent.getParcelableExtra("file_info");
        switch (intent.getAction()) {
            // 开始下载
            case ACTION_START:
                mInitThread = new InitThread(mFileInfo);
                DownloadTask.mExecutorService.execute(mInitThread);
                break;
            // 暂停下载
            case ACTION_PAUSE:
                DownloadTask task = mTasks.get(mFileInfo.getId());
                if (task != null) {
                    // 暂停下载
                    task.isPause = true;
                }
                break;
            default:
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * 初始化下载任务的线程
     */
    class InitThread extends Thread {

        private FileInfo fileInfo = null;

        public InitThread(FileInfo fileInfo) {
            this.fileInfo = fileInfo;
        }

        @Override
        public void run() {

            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            try {
                URL url = new URL(fileInfo.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setReadTimeout(3000);
                conn.setRequestMethod("GET");
                File fileDir = new File(PATH);
                if (!fileDir.exists()) {
                    fileDir.mkdir();
                }
                int length = -1;
                if (conn.getResponseCode() == 200) {
                    length = conn.getContentLength();
                }
                if (length < 0) {
                    return;
                }
                File file = new File(fileDir, fileInfo.getName());
                raf = new RandomAccessFile(file, "rwd");
                raf.setLength(length);
                fileInfo.setLength(length);
                // 插入数据库中
                if (mFileDao.isExists(fileInfo.getUrl())) {
                    fileInfo = mFileDao.get(fileInfo.getUrl());
                    fileInfo.setLength(length);
                    mFileDao.updateLength(fileInfo.getId(), length);
                } else {
                    mFileDao.add(fileInfo);
                }
                // 发送消息启动下载任务
                mHandler.obtainMessage(MSG_INIT, fileInfo).sendToTarget();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
                CloseUtil.close(raf);
            }

        }
    }
}
