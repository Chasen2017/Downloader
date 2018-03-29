package com.example.chasen.downloader.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.chasen.downloader.model.FileInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chasen on 18-3-28.
 * 文件信息数据库常用操作接口的实现类
 */

public class FileInfoDaoImpl implements IFileInfoDao {

    private DBHelper mDBHelper;
    private static volatile IFileInfoDao mInfoDao = null;

    private FileInfoDaoImpl(Context context) {
        mDBHelper = DBHelper.getInstance(context);
    }

    public static IFileInfoDao getInstance(Context context) {
        if (mInfoDao == null) {
            synchronized (FileInfoDaoImpl.class) {
                if (mInfoDao == null) {
                    mInfoDao = new FileInfoDaoImpl(context);
                }
            }
        }
        return mInfoDao;
    }

    @Override
    public void add(FileInfo fileInfo) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        String addInfo = "insert into file_info(name, url, image_url, length, finished, is_finished) values(?,?,?,?,?,?)";
        db.execSQL(addInfo,
                new Object[]{fileInfo.getName(),
                        fileInfo.getUrl(),
                        fileInfo.getImageUrl(),
                        fileInfo.getLength(),
                        fileInfo.getFinished(),
                        fileInfo.isFinished() ? 1 : 0});
        db.close();
    }

    @Override
    public void addAll(List<FileInfo> fileInfoList) {
        if (fileInfoList == null || fileInfoList.size() == 0)
            return;
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        String addInfo = "insert into file_info(name, url, image_url, length, finished, is_finished) values(?,?,?,?,?,?)";
        for (FileInfo fileInfo : fileInfoList) {
            db.execSQL(addInfo,
                    new Object[]{fileInfo.getName(),
                            fileInfo.getUrl(),
                            fileInfo.getImageUrl(),
                            fileInfo.getLength(),
                            fileInfo.getFinished(),
                            fileInfo.isFinished() ? 1 : 0});
        }
        db.close();
    }

    @Override
    public void delete(int id) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        String delete = "delete from file_info where id = ?";
        db.execSQL(delete, new Object[]{id});
        db.close();
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        String delete = "delete from file_info";
        db.execSQL(delete);
        db.close();
    }

    @Override
    public FileInfo get(int id) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String sql = "select * from file_info where id = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(id)});
        FileInfo fileInfo = null;
        while (cursor.moveToNext()) {
            fileInfo = new FileInfo();
            fileInfo.setId(id);
            fileInfo.setName(cursor.getString(cursor.getColumnIndex("name")));
            fileInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            fileInfo.setImageUrl(cursor.getString(cursor.getColumnIndex("image_url")));
            fileInfo.setLength(cursor.getInt(cursor.getColumnIndex("length")));
            fileInfo.setFinished(cursor.getInt(cursor.getColumnIndex("finished")));
            boolean isFinish = cursor.getInt(cursor.getColumnIndex("is_finished")) == 1;
            fileInfo.setFinished(isFinish);
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return fileInfo;
    }

    @Override
    public FileInfo get(String url) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String sql = "select * from file_info where url = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{url});
        FileInfo fileInfo = null;
        while (cursor.moveToNext()) {
            fileInfo = new FileInfo();
            fileInfo.setId(cursor.getInt(cursor.getColumnIndex("id")));
            fileInfo.setName(cursor.getString(cursor.getColumnIndex("name")));
            fileInfo.setUrl(url);
            fileInfo.setImageUrl(cursor.getString(cursor.getColumnIndex("image_url")));
            fileInfo.setLength(cursor.getInt(cursor.getColumnIndex("length")));
            fileInfo.setFinished(cursor.getInt(cursor.getColumnIndex("finished")));
            boolean isFinish = cursor.getInt(cursor.getColumnIndex("is_finished")) == 1;
            fileInfo.setFinished(isFinish);
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return fileInfo;
    }

    @Override
    public List<FileInfo> getFinished() {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String sql = "select * from file_info where is_finished = 1";
        Cursor cursor = db.rawQuery(sql, null);
        List<FileInfo> fileInfoList = new ArrayList<>();
        FileInfo fileInfo = null;
        while (cursor.moveToNext()) {
            fileInfo = new FileInfo();
            fileInfo.setId(cursor.getInt(cursor.getColumnIndex("id")));
            fileInfo.setName(cursor.getString(cursor.getColumnIndex("name")));
            fileInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            fileInfo.setImageUrl(cursor.getString(cursor.getColumnIndex("image_url")));
            fileInfo.setLength(cursor.getInt(cursor.getColumnIndex("length")));
            fileInfo.setFinished(cursor.getInt(cursor.getColumnIndex("finished")));
            fileInfo.setFinished(true);

            fileInfoList.add(fileInfo);
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return fileInfoList;
    }

    @Override
    public List<FileInfo> getUnfinished() {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String sql = "select * from file_info where is_finished = 0";
        Cursor cursor = db.rawQuery(sql, null);
        List<FileInfo> fileInfoList = new ArrayList<>();
        FileInfo fileInfo = null;
        while (cursor.moveToNext()) {
            fileInfo = new FileInfo();
            fileInfo.setId(cursor.getInt(cursor.getColumnIndex("id")));
            fileInfo.setName(cursor.getString(cursor.getColumnIndex("name")));
            fileInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            fileInfo.setImageUrl(cursor.getString(cursor.getColumnIndex("image_url")));
            fileInfo.setLength(cursor.getInt(cursor.getColumnIndex("length")));
            fileInfo.setFinished(cursor.getInt(cursor.getColumnIndex("finished")));
            fileInfo.setFinished(false);

            fileInfoList.add(fileInfo);
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return fileInfoList;
    }

    @Override
    public List<FileInfo> getAll() {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String sql = "select * from file_info";
        Cursor cursor = db.rawQuery(sql, null);
        List<FileInfo> fileInfoList = new ArrayList<>();
        FileInfo fileInfo = null;
        while (cursor.moveToNext()) {
            fileInfo = new FileInfo();
            fileInfo.setId(cursor.getInt(cursor.getColumnIndex("id")));
            fileInfo.setName(cursor.getString(cursor.getColumnIndex("name")));
            fileInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            fileInfo.setImageUrl(cursor.getString(cursor.getColumnIndex("image_url")));
            fileInfo.setLength(cursor.getInt(cursor.getColumnIndex("length")));
            fileInfo.setFinished(cursor.getInt(cursor.getColumnIndex("finished")));
            boolean isFinished = cursor.getInt(cursor.getColumnIndex("is_finished")) == 1;
            fileInfo.setFinished(isFinished);
            fileInfoList.add(fileInfo);
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return fileInfoList;
    }

    @Override
    public void update(int id, int finished, boolean isFinished) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        String sql = "update file_info set finished = ?, is_finished = ? where id = ?";
        db.execSQL(sql, new Object[]{finished, isFinished ? 1 : 0, id});
    }

    @Override
    public boolean isExists(String url) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String sql = "select * from file_info where url = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{url});
        boolean isExists = cursor.moveToNext();
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return isExists;
    }
}
