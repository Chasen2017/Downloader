package com.example.chasen.downloader.frags.downloading;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chasen.downloader.R;
import com.example.chasen.downloader.adapter.MyAdapter;
import com.example.chasen.downloader.common.Data;
import com.example.chasen.downloader.db.FileInfoDaoImpl;
import com.example.chasen.downloader.db.IFileInfoDao;
import com.example.chasen.downloader.model.FileInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 正在下载的界面
 */
public class DownloadingFragment extends Fragment {

    private Unbinder mUnbinder;
    private MyAdapter mAdapter;

    @BindView(R.id.recycler_downloading)
    RecyclerView mRecycler;

    public DownloadingFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_downloading, container, false);
        mUnbinder = ButterKnife.bind(this, view);
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
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

}
