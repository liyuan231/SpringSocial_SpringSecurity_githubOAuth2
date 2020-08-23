package com.liyuan.model;

public class TunedCOSObject {
    /**
     * 可以理解为文件的名字，但附上了路径
     */
    private String key;
    private long fileSize;

    public TunedCOSObject() {
    }

    public TunedCOSObject(String key, long fileSize) {
        this.key = key;
        this.fileSize = fileSize;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
}
