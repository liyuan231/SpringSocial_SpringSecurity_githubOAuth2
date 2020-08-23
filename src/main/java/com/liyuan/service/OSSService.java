package com.liyuan.service;

import com.liyuan.model.TunedCOSObject;

import java.util.List;

public interface OSSService{
    List<TunedCOSObject> listObjects();

    void delete(String key);
}
