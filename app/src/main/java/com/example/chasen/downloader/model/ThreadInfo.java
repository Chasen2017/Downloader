package com.example.chasen.downloader.model;

/**
 * Created by chasen on 18-3-26.
 *
 * 线程信息实体类
 */

public class ThreadInfo {

    private int id;         // 线程ID
    private String url;     // 线程对应下载的url
    private int start;      // 线程下载的起始点
    private int end;        // 线程下载的终点
    private int finished;   // 已下载的大小

    public ThreadInfo() {
    }

    public ThreadInfo(int id, String url, int start, int end, int finished) {
        this.id = id;
        this.url = url;
        this.start = start;
        this.end = end;
        this.finished = finished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ThreadInfo that = (ThreadInfo) o;

        if (id != that.id) return false;
        if (start != that.start) return false;
        if (end != that.end) return false;
        if (finished != that.finished) return false;
        return url != null ? url.equals(that.url) : that.url == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + start;
        result = 31 * result + end;
        result = 31 * result + finished;
        return result;
    }

    @Override
    public String toString() {
        return "ThreadInfo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", finished=" + finished +
                '}';
    }
}
