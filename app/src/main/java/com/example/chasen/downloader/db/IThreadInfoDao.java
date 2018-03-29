package com.example.chasen.downloader.db;

import com.example.chasen.downloader.model.ThreadInfo;

import java.util.List;


/**
 * Created by chasen on 18-3-28.
 * 线程信息操作类
 * 封装常用的线程信息操作
 */

public interface IThreadInfoDao {

    void add(ThreadInfo threadInfo);

    void delete(String url);

    List<ThreadInfo> getThreads(String url);

    List<ThreadInfo> getThreads();

    void updateThreadInfo(String url, int id, int finished);
}
