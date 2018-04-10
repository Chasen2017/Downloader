package com.example.chasen.downloader.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.chasen.downloader.model.ThreadInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chasen on 18-3-28.
 * 线程信息常用操作实现类
 */

public class ThreadInfoImpl implements IThreadInfoDao {


    private DBHelper mDBHelper = null;
    private static volatile IThreadInfoDao mThreadInfoDao = null;

    private ThreadInfoImpl(Context context) {
        mDBHelper = DBHelper.getInstance(context);
    }

    public static IThreadInfoDao getInstance(Context context) {
        if (mThreadInfoDao == null) {
            synchronized (ThreadInfoImpl.class) {
                if (mThreadInfoDao == null) {
                    mThreadInfoDao = new ThreadInfoImpl(context);
                }
            }
        }
        return mThreadInfoDao;
    }


    @Override
    public void add(ThreadInfo threadInfo) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        String sql = "insert into thread_info(url, start, end, finished) values (?,?,?,?)";
        db.execSQL(sql,
                new Object[]{
                        threadInfo.getUrl(),
                        threadInfo.getStart(),
                        threadInfo.getEnd(),
                        threadInfo.getFinished()
                });
      /*  db.close();*/
    }

    @Override
    public void delete(String url) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        String sql = "delete from thread_info where url = ?";
        db.execSQL(sql, new Object[]{url});
       /* db.close();*/
    }

    @Override
    public List<ThreadInfo> getThreads(String url) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        List<ThreadInfo> threadInfoList = new ArrayList<>();
        ThreadInfo threadInfo = null;
        String sql = "select * from thread_info where url = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{url});
        while (cursor.moveToNext()) {
            threadInfo = new ThreadInfo();
            threadInfo.setId(cursor.getInt(cursor.getColumnIndex("id")));
            threadInfo.setUrl(url);
            threadInfo.setStart(cursor.getInt(cursor.getColumnIndex("start")));
            threadInfo.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
            threadInfo.setFinished(cursor.getInt(cursor.getColumnIndex("finished")));
            threadInfoList.add(threadInfo);
        }
        if (cursor != null) {
            cursor.close();
        }
        /*db.close();*/
        return threadInfoList;
    }

    @Override
    public List<ThreadInfo> getThreads() {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        List<ThreadInfo> threadInfoList = new ArrayList<>();
        ThreadInfo threadInfo = null;
        String sql = "select * from thread_info";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            threadInfo = new ThreadInfo();
            threadInfo.setId(cursor.getInt(cursor.getColumnIndex("id")));
            threadInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            threadInfo.setStart(cursor.getInt(cursor.getColumnIndex("start")));
            threadInfo.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
            threadInfo.setFinished(cursor.getInt(cursor.getColumnIndex("finished")));
            threadInfoList.add(threadInfo);
        }
        if (cursor != null) {
            cursor.close();
        }
      /*  db.close();*/
        return threadInfoList;
    }

    @Override
    public void updateThreadInfo(String url, int id, int finished) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        String sql = "update thread_info set finished = ? where id = ? and url = ?";
        db.execSQL(sql, new Object[]{finished, id, url});
     /*   db.close();*/
    }
}
