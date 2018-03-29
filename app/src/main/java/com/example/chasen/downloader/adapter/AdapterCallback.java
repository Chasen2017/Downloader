package com.example.chasen.downloader.adapter;

/**
 * Created by chasen on 18-3-26.
 * ViewHolder进行更新的接口
 */

public interface AdapterCallback<Data> {
    void update(Data data, RecyclerAdapter.ViewHolder<Data> holder);
}
