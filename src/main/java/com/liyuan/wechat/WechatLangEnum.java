package com.liyuan.wechat;

public enum WechatLangEnum {
    ZH_CN("zh-CN"),EN("en");

    WechatLangEnum(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
