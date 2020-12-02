package com.bjtu.campus_information_platform.util.network;

import com.bjtu.campus_information_platform.model.Test;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * 所有的请求接口
 */
public class HttpRequest {

    /**
     * @param params 入参
     * @param callback 回调接口
     */
    public static void getTestApi(RequestParams params, ResponseCallback callback) {
        RequestMode.getRequest("https://www.hihia.top/android-back-end-api/test/getList", params, callback, new TypeToken<List<Test>>(){}.getType());
    }

    /**
     * @param params 入参
     * @param callback 回调接口
     */
    public static void postTestApi(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest("https://www.hihia.top/android-back-end-api/test/login", params, callback, null);
    }
}