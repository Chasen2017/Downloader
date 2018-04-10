package com.example.chasen.downloader.frags.downloaded;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chasen.downloader.R;
import com.example.chasen.downloader.adapter.MyAdapter;
import com.example.chasen.downloader.adapter.RecyclerAdapter;
import com.example.chasen.downloader.db.FileInfoDaoImpl;
import com.example.chasen.downloader.db.IFileInfoDao;
import com.example.chasen.downloader.model.FileInfo;
import com.example.chasen.downloader.util.MyUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 已经下载完成的页面
 */
public class DownloadedFragment extends Fragment {


    private static final String TAG = "DownloadedFragment";

    Unbinder mUnBinder;
    private List<FileInfo> mFileInfoList;
    private RecyclerAdapter<FileInfo> mAdapter;
    private IFileInfoDao mFileDao;

    @BindView(R.id.recycler_downloaded)
    RecyclerView mRecycler;


    public DownloadedFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_downloaded, container, false);
        mUnBinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        init();
        return view;
    }

    private void init() {
        mFileDao = FileInfoDaoImpl.getInstance(getContext());
        mFileInfoList = mFileDao.getFinished();
        if (mFileInfoList == null) {
            mFileInfoList = new ArrayList<>();
        }
        mAdapter = new RecyclerAdapter<FileInfo>(mFileInfoList, null) {
            @Override
            protected ViewHolder<FileInfo> onCreateViewHolder(View root, int viewType) {
                return new DownloadedFragment.ViewHolder(root);
            }

            @Override
            protected int getItemViewType(int position, FileInfo fileInfo) {
                return R.layout.item_downloaded;
            }
        };
        mRecycler.setAdapter(mAdapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    // 下载完成后，更新已下载的界面
    @Subscribe
    public void update(String update) {
        mFileInfoList = mFileDao.getFinished();
        if (mFileInfoList == null) {
            mFileInfoList = new ArrayList<>();
        }
        mAdapter.replace(mFileInfoList);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
        EventBus.getDefault().unregister(this);
    }


    class ViewHolder extends RecyclerAdapter.ViewHolder<FileInfo> {
        @BindView(R.id.im)
        ImageView im;
        @BindView(R.id.txt_file_name)
        TextView nameTv;
        @BindView(R.id.txt_file_size)
        TextView sizeTv;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(FileInfo fileInfo) {
            Glide.with(getContext())
                    .load(fileInfo.getImageUrl())
                    .centerCrop()
                    .into(im);
            nameTv.setText(fileInfo.getName());
            sizeTv.setText(MyUtil.formatLength(fileInfo.getLength()));
        }
    }
}
