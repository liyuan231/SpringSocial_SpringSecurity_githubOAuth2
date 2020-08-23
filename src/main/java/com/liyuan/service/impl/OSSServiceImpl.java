package com.liyuan.service.impl;

import com.liyuan.model.TunedCOSObject;
import com.liyuan.service.OSSService;
import com.liyuan.utils.qcloud.QCloudCosUtils;
import com.qcloud.cos.model.COSObjectSummary;
import com.qcloud.cos.model.ObjectListing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OSSServiceImpl implements OSSService {
    QCloudCosUtils qCloudCosUtils;

    @Autowired
    public void setqCloudCosUtils(QCloudCosUtils qCloudCosUtils) {
        this.qCloudCosUtils = qCloudCosUtils;
    }

    @Override
    public List<TunedCOSObject> listObjects() {
        ObjectListing objectListing = qCloudCosUtils.listObjects();
        List<COSObjectSummary> objectSummaries = objectListing.getObjectSummaries();
        List<TunedCOSObject> list = new ArrayList<>();
        for (COSObjectSummary cosObjectSummary : objectSummaries) {
            String key = cosObjectSummary.getKey();
            long size = cosObjectSummary.getSize();
            list.add(new TunedCOSObject(key, size));
        }
        return list;
    }

    @Override
    public void delete(String key) {
        qCloudCosUtils.delete(key);
    }
}
