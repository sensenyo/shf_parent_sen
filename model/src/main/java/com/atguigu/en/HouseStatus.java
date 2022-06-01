package com.atguigu.en;

import lombok.Data;

/**
 * 包名:com.atguigu.com.atguigu.en
 *
 * @author Leevi
 * 日期2022-03-26  22:51
 */
public enum HouseStatus {
    PUBLISHED(1,"已发布"), UNPUBLISHED(0,"未发布");
    private int code;
    private String message;

    HouseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
