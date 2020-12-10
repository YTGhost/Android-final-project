package com.bjtu.campus_information_platform.util.network;

public class OkHttpException extends Exception{

    private static final long serialVersionUID = 1L;

    private int ecode;  // 错误码
    private String emsg;  // 错误消息

    public OkHttpException(int ecode, String emsg) {
        this.ecode = ecode;
        this.emsg = emsg;
    }

    public int getEcode() {
        return ecode;
    }

    public String getEmsg() {
        return emsg;
    }
}
