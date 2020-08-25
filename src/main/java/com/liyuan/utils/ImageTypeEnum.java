package com.liyuan.utils;

public enum ImageTypeEnum {
    WEBP("webp"), BMP("bmp"), PCX("pcx"), TIF("tif"), GIF("gif"), JPEG("jpeg"),
    TGA("tga"), EXIF("exif"), FPX("fpx"), SVG("svg"), PSD("psd"), CDR("cdr"),
    PCD("pcd"), DXF("dxf"), UFO("ufo"), EPS("eps"), AI("ai"), PNG("png"),
    HDRI("hdri"), RAW("raw"), WNF("wnf"), FLIC("flic"), EMF("emf"), ICO("ico"),
    JPG("jpg"), WMF("wmf");
    private String value;

    ImageTypeEnum(String value) {
        this.value = value;
    }

    public static boolean isImageType(String suffix) {
        ImageTypeEnum[] values = ImageTypeEnum.values();
        for (ImageTypeEnum imageTypeEnum : values) {
            if (imageTypeEnum.value.equals(suffix)) {
                return true;
            }
        }
        return false;
    }
}
