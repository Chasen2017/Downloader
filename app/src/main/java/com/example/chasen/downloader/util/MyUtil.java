package com.example.chasen.downloader.util;

import java.text.DecimalFormat;

/**
 * Created by chasen on 18-3-28.
 * 工具类
 * 常用操作
 */

public class MyUtil {

    /**
     * 计算下载进度
     * @param length 文件大小
     * @param finished 已下载的文件大小
     * @return 下载的进度
     */
    public static int getProgress(int finished, int length) {
        if (length == 0) return 0;
        int progress = 100*finished/length;
        return progress;
    }

    /**
     * 格式化文件大小显示
     * @param length 文件大小
     * @return 格式化后的文件大小
     */
    public static String formatLength(int length) {
        DecimalFormat df = new DecimalFormat("0.00");
        String s;
        double size;
        if (length >= 1024*1024*1024) { // G
            size = (length/(1024*1024))/1024D;
            s = df.format(size)+"G";
        } else if (length >= 1024 * 1024) { // M
            size = (length/1024)/1024D;
            s = df.format(size)+"M";
        } else if (length >= 1024) { // K
            size = length/1024D;
            s = df.format(size)+"Kb";
        } else {  // B
            size = length;
            s = df.format(size)+"B";
        }

        return s;
    }

    /**
     * 格式化网速显示
     * @param speed 网速
     * @return 格式化后的网速
     */
    public static String formatSpeed(long speed) {
        DecimalFormat df = new DecimalFormat("0.00");
        String s;
        double size;
         if (speed >= 1024 * 1024) { // M
            size = (speed/1024)/1024D;
            s = df.format(size)+"M/s";
        } else if (speed >= 1024) { // K
            size = speed/1024D;
            s = df.format(size)+"Kb/s";
        } else {  // B
            s = speed +"B/s";
        }

        return s;
    }

}
