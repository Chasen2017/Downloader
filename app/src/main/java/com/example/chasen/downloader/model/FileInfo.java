package com.example.chasen.downloader.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chasen on 18-3-26.
 *
 * 文件实体类
 */

public class FileInfo implements Parcelable{

    private int id;             // id
    private String name;        // 文件名
    private String imageUrl;    // 图片URL
    private String url;         // 下载改文件的URL
    private int length;          // 文件大小
    private int finished;       // 已下载的大小
    private boolean isFinished; // 是否已经下载完成
    private long speed;
    private boolean isDownloading;

    public FileInfo() {
    }

    public FileInfo(int id, String name, String imageUrl, String url, int length, int finished, boolean isFinished) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.url = url;
        this.length = length;
        this.finished = finished;
        this.isFinished = isFinished;
    }

    protected FileInfo(Parcel in) {
        id = in.readInt();
        name = in.readString();
        imageUrl = in.readString();
        url = in.readString();
        length = in.readInt();
        finished = in.readInt();
        isFinished = in.readByte() != 0;
        speed = in.readLong();
        isDownloading = in.readByte() != 0;
    }

    public static final Creator<FileInfo> CREATOR = new Creator<FileInfo>() {
        @Override
        public FileInfo createFromParcel(Parcel in) {
            return new FileInfo(in);
        }

        @Override
        public FileInfo[] newArray(int size) {
            return new FileInfo[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public long getSpeed() {
        return speed;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public boolean isDownloading() {
        return isDownloading;
    }

    public void setDownloading(boolean downloading) {
        isDownloading = downloading;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileInfo fileInfo = (FileInfo) o;

        if (id != fileInfo.id) return false;
        if (length != fileInfo.length) return false;
        if (finished != fileInfo.finished) return false;
        if (isFinished != fileInfo.isFinished) return false;
        if (speed != fileInfo.speed) return false;
        if (isDownloading != fileInfo.isDownloading) return false;
        if (name != null ? !name.equals(fileInfo.name) : fileInfo.name != null) return false;
        if (imageUrl != null ? !imageUrl.equals(fileInfo.imageUrl) : fileInfo.imageUrl != null)
            return false;
        return url != null ? url.equals(fileInfo.url) : fileInfo.url == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + length;
        result = 31 * result + finished;
        result = 31 * result + (isFinished ? 1 : 0);
        result = 31 * result + (int) (speed ^ (speed >>> 32));
        result = 31 * result + (isDownloading ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", url='" + url + '\'' +
                ", length=" + length +
                ", finished=" + finished +
                ", isFinished=" + isFinished +
                ", speed=" + speed +
                ", isDownloading=" + isDownloading +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(imageUrl);
        dest.writeString(url);
        dest.writeInt(length);
        dest.writeInt(finished);
        dest.writeByte((byte) (isFinished ? 1 : 0));
        dest.writeLong(speed);
        dest.writeByte((byte)(isDownloading ? 1: 0));
    }
}
