package com.liyuan.model;

public enum RoleType {
    ORDINARY(0),
    GITHUB(10);
    private Integer value;

    public Integer value() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    RoleType(Integer value) {
        this.value = value;
    }
}
