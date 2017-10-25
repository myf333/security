package com.myf.security.model;

/**
 * Created by myf on 2017/10/25.
 */
public class ValidCode {
    private boolean success;
    private String msg;

    public ValidCode(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
