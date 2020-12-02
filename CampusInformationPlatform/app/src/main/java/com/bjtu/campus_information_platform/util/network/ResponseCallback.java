package com.bjtu.campus_information_platform.util.network;

public interface ResponseCallback {

    // 请求成功回调事件处理
    void onSuccess(Object responseObj);

    // 请求失败回调事件处理
    void onFailure(OkHttpException failuer);
}
