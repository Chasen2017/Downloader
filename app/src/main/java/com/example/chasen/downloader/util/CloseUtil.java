package com.example.chasen.downloader.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by chasen on 18-3-29.
 */

public class CloseUtil{

    /**
     * 关闭流
     * @param closeable
     */
    public static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
