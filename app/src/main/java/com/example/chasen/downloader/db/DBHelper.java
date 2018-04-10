package com.example.chasen.downloader.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chasen on 18-3-28.
 * 数据库工具类
 * 用于创建、升级数据库
 */

public class DBHelper extends SQLiteOpenHelper {

    // 数据库名称
    public static final String DB_NAME = "download.db";
    // 数据库版本
    public static final int VERSION = 5;

    // 创建文件信息表语句
    public static final String CREATE_FILE_INFO = "create table file_info(" +
            "id integer primary key autoincrement, " +
            "name text," +
            "url text," +
            "image_url text," +
            "length integer," +
            "finished integer," +
            "is_finished integer)"; // 0标识未完成，1标识完成
    // 创建线程信息表语句
    public static final String CREATE_THREAD_INFO = "create table thread_info(" +
            "id integer primary key autoincrement," +
            "url text," +
            "start integer, " +
            "end integer," +
            "finished integer)";
    // 删除文件信息表语句
    public static final String DROP_FILE_INFO = "drop table if exists file_info";
    // 删除线程信息表语句
    public static final String DROP_THREAD_INFO = "drop table if exists thread_info";

    // 单例模式
    public static volatile DBHelper mDBHelper = null;


    private DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }
    // double check
    public static DBHelper getInstance(Context context) {
        if (mDBHelper == null) {
            synchronized (DBHelper.class) {
                if (mDBHelper == null) {
                    mDBHelper = new DBHelper(context);
                }
            }
        }
        return mDBHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FILE_INFO);
        db.execSQL(CREATE_THREAD_INFO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_FILE_INFO);
        db.execSQL(DROP_THREAD_INFO);
        onCreate(db);
    }
}
