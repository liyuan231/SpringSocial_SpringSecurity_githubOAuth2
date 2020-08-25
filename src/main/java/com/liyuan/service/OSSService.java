package com.liyuan.service;

import com.liyuan.model.TunedCOSObject;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public interface OSSService {
    List<TunedCOSObject> listObjects();

    void delete(String key);

    URL upload(String filename, File file);

    URL upload(String filename, InputStream file);
}
