package com.bjtu.campus_information_platform.util.network;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MyRequest {
    /**
     * 创建Get请求的Request
     */
    public static Request createGetRequest(String url, RequestParams params) {
        StringBuilder urlBuilder = new StringBuilder(url).append("?");

        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                urlBuilder
                        .append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append("&");
            }
        }

        return new Request.Builder().url(urlBuilder.substring(0, urlBuilder.length() - 1))
                .get().build();
    }

    /**
     * 创建Post请求的Request
     *
     * @return 返回一个创建好的Request对象
     */
    public static Request createPostRequest(String url, RequestParams params) {
        JSONObject object = new JSONObject();
        //将请求参数逐一遍历添加
        for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
            try {
                object.put(entry.getKey(), entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, object.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return request;
    }

    /**
     * 上传图片
     * @return 返回一个创建好的Request对象
     */
    public static Request createMultipartRequest(String url, RequestParams params, File file) {

        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file",file.getName(),
                        RequestBody.create(MediaType.parse("image/*"), file))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        return request;
    }
}
