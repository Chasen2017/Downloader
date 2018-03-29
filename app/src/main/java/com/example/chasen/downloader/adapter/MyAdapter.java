package com.example.chasen.downloader.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chasen.downloader.R;
import com.example.chasen.downloader.model.FileInfo;
import com.example.chasen.downloader.util.MyUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chasen on 18-3-28.
 * 下载页面的设配器
 */

public class MyAdapter extends RecyclerAdapter {

    private List<FileInfo> mFileInfoList;
    private Context mContext;

    public MyAdapter(Context context, List<FileInfo> fileInfoList) {
        super(fileInfoList, null);
        this.mFileInfoList = fileInfoList;
        this.mContext = context;
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
    public void update(int id, int finished, int speed) {
        int index = findIndex(id);
        FileInfo fileInfo = mFileInfoList.get(index);
        fileInfo.setFinished(finished);
        fileInfo.setSpeed(speed);
        notifyItemChanged(index);
    }

    private int findIndex(int id) {
        for (int i = 0; i < mFileInfoList.size(); i++) {
            if (mFileInfoList.get(i).getId() == id) {
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
        @BindView(R.id.btn)
        ImageButton btn;

        private boolean isPause = false;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(FileInfo fileInfo) {
            Glide.with(mContext)
                    .load(fileInfo.getImageUrl())
                    .centerCrop()
                    .error(R.drawable.ic_launcher_foreground)
                    .into(icon);
            fileNameTxt.setText(fileInfo.getName());
            String s = MyUtil.formatLength(fileInfo.getFinished()) + "/" + MyUtil.formatLength(fileInfo.getLength());
            fileSizeTxt.setText(s);
            if (isPause) {
                statusTxt.setText(MyUtil.formatSpeed(fileInfo.getSpeed()));
            } else {
                statusTxt.setText("暂停");
            }
            pb.setProgress(MyUtil.getProgress(fileInfo.getFinished(), fileInfo.getLength()));
        }

        @OnClick(R.id.btn)
        void onBtnClick() {
            btn.setImageResource(isPause ? R.drawable.ic_start : R.drawable.ic_pause);
            statusTxt.setText(isPause ? "暂停": "正在下载");
            isPause = !isPause;
        }

        @OnClick(R.id.ll_cell)
        void onCellLayoutClick() {
            onBtnClick();
        }
    }
}
