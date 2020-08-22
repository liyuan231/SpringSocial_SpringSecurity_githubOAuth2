package com.liyuan.model;

public interface Rest<T> {
    void setHttpStatus(int httpStatus);
    void setData(T data);
    void setMessage(String message);
    void setIdentifier(String identifier);
}
