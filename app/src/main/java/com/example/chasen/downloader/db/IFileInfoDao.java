package com.example.chasen.downloader.db;

import com.example.chasen.downloader.model.FileInfo;

import java.util.List;

/**
 * Created by chasen on 18-3-28.
 * 文件信息工具类
 * 封装常用的文件信息操作
 */

public interface IFileInfoDao {

    /**
     * 添加一个文件信息到数据库中
     *
     * @param fileInfo 文件信息
     */
    void add(FileInfo fileInfo);

    /**
     * 添加一堆文件信息到数据库中
     *
     * @param fileInfoList 文件信息集合
     */
    void addAll(List<FileInfo> fileInfoList);

    /**
     * 删除一个文件信息
     *
     * @param id 文件信息的ID
     */
    void delete(int id);

    /**
     * 删除全部文件信息
     */
    void deleteAll();

    /**
     * 通过文件信息的Id得到一个文件信息
     *
     * @param id 文件信息的ID
     * @return FileInfo
     */
    FileInfo get(int id);

    /**
     * 通过文件信息的下载url得到一个文件信息
     *
     * @param url 文件下载的url
     * @return FileInfo
     */
    FileInfo get(String url);

    /**
     * 得到数据库中下载完成的文件信息
     *
     * @return List<FileInfo>
     */
    List<FileInfo> getFinished();

    /**
     * 得到数据库中未完成下载的文件信息
     *
     * @return List<FileInfo>
     */
    List<FileInfo> getUnfinished();

    /**
     * 获取全部的文件信息
     * @return List<FileInfo>
     */
    List<FileInfo> getAll();

    /**
     * 更新文件信息
     *
     * @param id 文件信息的ID
     * @param finished 已下载的大小
     * @param isFinished 是否已经完成
     */
    void update(int id, int finished, boolean isFinished);

    /**
     * 文件信息是否存在
     * @param url 文件的下载url
     * @return 存在返回true
     */
    boolean isExists(String url);
}
