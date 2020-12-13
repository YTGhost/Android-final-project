package com.bjtu.campus_information_platform.util.network;

import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

/*
* 请求模式
*/
public class RequestMode {

    /**
     * GET请求
     * @param url URL请求地址
     * @param params 入参
     * @param callback 回调接口
     * @param clazz 需要解析的实体类
     */
    public static void getRequest(String url, RequestParams params,
                                  ResponseCallback callback, Type clazz) {
        MyOkHttpClient.get(MyRequest.createGetRequest(url, params),
                new ResposeDataHandle(callback, clazz));
    }

    /**
     * POST请求
     * @param url URL请求地址
     * @param params 入参
     * @param callback 回调接口
     * @param clazz 需要解析的实体类
     */
    public static void postRequest(String url, RequestParams params,
                                   ResponseCallback callback, Type clazz) {
        MyOkHttpClient.post(MyRequest.createPostRequest(url, params),
                new ResposeDataHandle(callback, clazz));
    }

    /**
     * 上传图片
     */
    public static void postMultipart(String url, RequestParams params,
                                     File file, ResponseCallback callback, Class<?> clazz) {
        MyOkHttpClient.post(MyRequest.createMultipartRequest(url, params, file),
                new ResposeDataHandle(callback, clazz));
    }

    /**
     * 下载图片 Get方式
     */
    public static void getLoadImg(String url,RequestParams params,String imgPath, ResponseByteCallback callback){
        MyOkHttpClient.downLadImg(MyRequest.createGetRequest(url, params),imgPath,callback);
    }
}
