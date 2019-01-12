package com.github.raphael008.enums;

public enum BlockedStatus {
    YES(1, "已封禁"),
    NO(0, "未封禁");

    private Integer index;
    private String value;

    public Integer getIndex() {
        return index;
    }

    public String getValue() {
        return value;
    }

    BlockedStatus(Integer index, String value) {
        this.index = index;
        this.value = value;
    }
}
