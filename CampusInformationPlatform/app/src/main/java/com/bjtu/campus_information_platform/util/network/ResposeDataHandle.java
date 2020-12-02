package com.bjtu.campus_information_platform.util.network;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/*
 * 封装回调接口和要转换的实体对象
 */
public class ResposeDataHandle {

    public ResponseCallback mListener = null;
    public Type mClass = null;

    public ResposeDataHandle(ResponseCallback mListener, Type mClass) {
        this.mListener = mListener;
        this.mClass = mClass;
    }
}
