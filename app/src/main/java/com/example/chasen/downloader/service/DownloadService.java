package com.example.chasen.downloader.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by chasen on 18-3-29.
 *
 * 执行下载的服务，实现后台下载
 */

public class DownloadService extends Service{


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * 初始化下载任务的线程
     */
    class InitThread extends Thread {


        @Override
        public void run() {

        }
    }
}
