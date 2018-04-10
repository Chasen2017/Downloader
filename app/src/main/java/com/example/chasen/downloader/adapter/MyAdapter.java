package com.example.chasen.downloader.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chasen.downloader.R;
import com.example.chasen.downloader.db.FileInfoDaoImpl;
import com.example.chasen.downloader.db.IFileInfoDao;
import com.example.chasen.downloader.model.FileInfo;
import com.example.chasen.downloader.service.DownloadService;
import com.example.chasen.downloader.util.MyUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chasen on 18-3-28.
 * 下载页面的设配器
 */

public class MyAdapter extends RecyclerAdapter {
    // 一个任务下载完成的标记
    private static final String FINISHED = "FINISHED";

    private List<FileInfo> mFileInfoList;
    private Context mContext;
    private IFileInfoDao mFileDao;

    public MyAdapter(Context context, List<FileInfo> fileInfoList) {
        super(fileInfoList, null);
        this.mFileInfoList = fileInfoList;
        this.mContext = context;
        mFileDao = FileInfoDaoImpl.getInstance(context);
    }

    @Override
    protected ViewHolder onCreateViewHolder(View root, int viewType) {
        return new ViewHolder(root);
    }

    @Override
    protected int getItemViewType(int position, Object o) {
        return R.layout.item_downloading;
    }

    // 更新下载进度、下载速度
    public void update(String url, int finished, long speed, int length, boolean isDownloading) {
        int index = findIndex(url);
        // 如果index = -1，找不到，不更新
        if(index == -1) return;

        FileInfo fileInfo = mFileInfoList.get(index);
        fileInfo.setDownloading(isDownloading);
        fileInfo.setFinished(finished);
        fileInfo.setSpeed(speed);
        fileInfo.setLength(length);
        // 如果下载好了,从本界面移除，更新数据库
        if (finished == length) {
            Log.e("madapter", "下载好了");
            fileInfo.setDownloading(false);
            fileInfo.setFinished(true);
            Log.e("madapter", "all: "+mFileDao.getAll().toString());
            mFileDao.update(fileInfo.getId(), finished, true);
            EventBus.getDefault().post(FINISHED);
            mFileInfoList.remove(index);
            notifyDataSetChanged();
            return;
        }
        notifyItemChanged(index);

    }

    private int findIndex(String url) {
        for (int i = 0; i < mFileInfoList.size(); i++) {
            if (mFileInfoList.get(i).getUrl().equals(url)) {
                return i;
            }
        }
        return -1;
    }


    class ViewHolder extends RecyclerAdapter.ViewHolder<FileInfo> {
        @BindView(R.id.im)
        ImageView icon;
        @BindView(R.id.txt_file_name)
        TextView fileNameTxt;
        @BindView(R.id.txt_file_size)
        TextView fileSizeTxt;
        @BindView(R.id.txt_status)
        TextView statusTxt;
        @BindView(R.id.progress)
        ProgressBar pb;
        @BindView(R.id.btn_start)
        ImageButton startBtn;
        @BindView(R.id.btn_pause)
        ImageButton pauseBtn;

        private FileInfo mFileInfo = null;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(FileInfo fileInfo) {
            mFileInfo = fileInfo;
            Glide.with(mContext)
                    .load(fileInfo.getImageUrl())
                    .centerCrop()
                    .error(R.drawable.ic_launcher_foreground)
                    .into(icon);
            fileNameTxt.setText(fileInfo.getName());
            String s = MyUtil.formatLength(fileInfo.getFinished()) + "/" + MyUtil.formatLength(fileInfo.getLength());
            fileSizeTxt.setText(s);
            if (fileInfo.getSpeed() != 0) {
                statusTxt.setText(MyUtil.formatSpeed(mFileInfo.getSpeed()));
            } else {
                statusTxt.setText("暂停");
            }
            pb.setMax(fileInfo.getLength());
            pb.setProgress(fileInfo.getFinished());
            if (mFileInfo.isDownloading()) {
                pauseBtn.setVisibility(View.VISIBLE);
                startBtn.setVisibility(View.GONE);
            } else {
                startBtn.setVisibility(View.VISIBLE);
                pauseBtn.setVisibility(View.GONE);
                statusTxt.setText("暂停");
            }
        }

        @OnClick(R.id.btn_start)
        void onBtnClick() {
            Intent intent = new Intent(mContext, DownloadService.class);
            intent.putExtra("file_info", mFileInfo);
            intent.setAction(DownloadService.ACTION_START);
            if (!mFileInfo.isDownloading()) {
                mContext.startService(intent);
                pauseBtn.setVisibility(View.VISIBLE);
                startBtn.setVisibility(View.GONE);
            }

        }

        @OnClick(R.id.btn_pause)
        void onPauseClick() {
            Intent intent = new Intent(mContext, DownloadService.class);
            intent.putExtra("file_info", mFileInfo);
            intent.setAction(DownloadService.ACTION_PAUSE);
            if (mFileInfo.isDownloading()) {
                mContext.startService(intent);
                startBtn.setVisibility(View.VISIBLE);
                pauseBtn.setVisibility(View.GONE);
            }

        }


        // 长按事件
        @OnClick(R.id.ll_cell)
        void onCellLayoutLongClick() {
            /*mFileInfoList.remove(mFileInfo);*/
        }
    }
}
