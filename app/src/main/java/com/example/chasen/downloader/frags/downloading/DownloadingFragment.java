package com.example.chasen.downloader.frags.downloading;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chasen.downloader.R;
import com.example.chasen.downloader.adapter.MyAdapter;
import com.example.chasen.downloader.common.Data;
import com.example.chasen.downloader.db.FileInfoDaoImpl;
import com.example.chasen.downloader.db.IFileInfoDao;
import com.example.chasen.downloader.model.FileInfo;
import com.example.chasen.downloader.service.DownloadService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 正在下载的界面
 */
public class DownloadingFragment extends Fragment {

    private static final String TAG = "DownloadingFragment";
    private static final int MSG_UPDATE = 1;

    private Unbinder mUnbinder;
    private MyAdapter mAdapter;

    @BindView(R.id.recycler_downloading)
    RecyclerView mRecycler;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
           switch (msg.what) {
               case MSG_UPDATE:
                   FileInfo info = (FileInfo) msg.obj;
                   Log.e("TAG", "info:"+info.toString());
                   mAdapter.update(info.getUrl(), info.getFinished(), info.getSpeed(), info.getLength(), info.isDownloading());
                   break;
               default:
                   break;
           }
        }
    };

    public DownloadingFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_downloading, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        init();
        return view;
    }

    private void init() {
        IFileInfoDao mFileDao = FileInfoDaoImpl.getInstance(getContext());
        mFileDao.deleteAll();
        mFileDao.addAll(Data.mFileInfoList);
        final List<FileInfo> mUnfinishedList = mFileDao.getUnfinished();
        mAdapter = new MyAdapter(getContext(), mUnfinishedList);
        mRecycler.setAdapter(mAdapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAnimation(null);
        // 关闭cell动画，解决闪烁问题
        ((SimpleItemAnimator)mRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    // 接收EventBus发送过来的消息
    @Subscribe
    public void updateInfo(FileInfo info) {
        mHandler.obtainMessage(MSG_UPDATE, info).sendToTarget();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        EventBus.getDefault().unregister(this);
    }



}
