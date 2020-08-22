package com.liyuan.model;

import java.io.Serializable;

public class RestBody<T> implements Rest<T>, Serializable {

    private static final long serialVersionUID = 5742147635271522003L;
    private int httpStatus = 200;
    private T data;
    private String message="";
    private String identifier="";

    public int getHttpStatus() {
        return httpStatus;
    }

    @Override
    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public T getData() {
        return data;
    }

    @Override
    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public static <T> Rest<T> ok() {
        RestBody<T> tRestBody = new RestBody<>();
        tRestBody.setHttpStatus(200);
        return tRestBody;
    }

    public static <T> Rest<T> ok(String message) {
        Rest<T> rest = new RestBody<T>();
        rest.setMessage(message);
        rest.setHttpStatus(200);
        return rest;
    }

    public static <T> Rest<T> okData(T data) {
        Rest<T> rest = new RestBody<>();
        rest.setData(data);
        rest.setHttpStatus(200);
        return rest;
    }

    public static <T> Rest<T> okData(T data, String message) {
        Rest<T> rest = new RestBody<>();
        rest.setData(data);
        rest.setMessage(message);
        rest.setHttpStatus(200);
        return rest;
    }

    public static <T> Rest<T> build(int httpStatus,
                                    T data,
                                    String message,
                                    String identifier) {
        Rest<T> rest = new RestBody<>();
        rest.setHttpStatus(httpStatus);
        rest.setData(data);
        rest.setMessage(message);
        rest.setIdentifier(identifier);
        return rest;
    }

    public static <T> Rest<T> failure(int httpStatus,
                                      String message) {
        Rest<T> rest = new RestBody<>();
        rest.setIdentifier("ERROR");
        rest.setMessage(message);
        rest.setHttpStatus(httpStatus);
        return rest;
    }
    public static <T> Rest<T> failure(int httpStatus,
                                      String message,
                                      T data) {
        Rest<T> rest = new RestBody<>();
        rest.setIdentifier("ERROR");
        rest.setData(data);
        rest.setMessage(message);
        rest.setHttpStatus(httpStatus);
        return rest;
    }

    @Override
    public String toString() {
        return "RestBody{" +
                "httpStatus=" + httpStatus +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", identifier='" + identifier + '\'' +
                '}';
    }
}
