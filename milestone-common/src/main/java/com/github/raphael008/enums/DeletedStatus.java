package com.github.raphael008.enums;

public enum DeletedStatus {
    YES(1, "已删除"),
    NO(0, "未删除");

    private Integer index;
    private String value;

    public Integer getIndex() {
        return index;
    }

    public String getValue() {
        return value;
    }

    DeletedStatus(Integer index, String value) {
        this.index = index;
        this.value = value;
    }
}
