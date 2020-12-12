package com.bjtu.campus_information_platform.util.network;

import com.bjtu.campus_information_platform.model.Account;
import com.bjtu.campus_information_platform.model.Course;
import com.bjtu.campus_information_platform.model.Step;
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

    /**
     * 登录接口
     * @param params 入参
     * @param callback 回调接口
     */
    public static void loginRequest(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest("https://www.hihia.top/android-back-end-api/account/login", params, callback, new TypeToken<Account>(){}.getType());
    }

    /**
     * 发送验证码接口
     * @param params 入参
     * @param callback 回调接口
     */
    public static void getCodeRequest(String email, RequestParams params, ResponseCallback callback) {
        RequestMode.getRequest("https://www.hihia.top/android-back-end-api/account/getRegisterCode/" + email, params, callback, null);
    }

    /**
     * 注册接口
     * @param params 入参
     * @param callback 回调接口
     */
    public static void registerRequest(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest("https://www.hihia.top/android-back-end-api/account/register", params, callback, null);
    }

    /**
     * 验证邮箱唯一性接口
     * @param params 入参
     * @param callback 回调接口
     */
    public static void judgeEmailRequest(String email, RequestParams params, ResponseCallback callback) {
        RequestMode.getRequest("https://www.hihia.top/android-back-end-api/account/judgeEmail/" + email, params, callback, null);
    }

    /**
     * 验证用户名唯一性接口
     * @param params 入参
     * @param callback 回调接口
     */
    public static void judgeNicknameRequest(String nickname, RequestParams params, ResponseCallback callback) {
        RequestMode.getRequest("https://www.hihia.top/android-back-end-api/account/judgeNickname/" + nickname, params, callback, null);
    }

    /**
     * 更新当前用户步数并获取所有用户步数
     * @param params 入参
     * @param callback 回调接口
     */
    public static void postStepApi(RequestParams params,ResponseCallback callback){
        RequestMode.postRequest("https://www.hihia.top/android-back-end-api/step/getStep",params,callback,new TypeToken<List<Step>>(){}.getType());
    }

    /**
     * 更新学生的一条课程记录
     * @param params 入参
     * @param callback 回调接口
     */
    public static void postClass(RequestParams params,ResponseCallback callback){
        RequestMode.postRequest("https://www.hihia.top/android-back-end-api/classlist/add",params,callback,null);
    }

    /**
     * 删除学生的一条记录
     * @param params 入参
     * @param callback 回调接口
     */
    public static void deleteClass(RequestParams params,ResponseCallback callback){
        RequestMode.postRequest("https://www.hihia.top/android-back-end-api/classlist/delete",params,callback,null);
    }

    /**
     * 获得学生的所有课程记录
     * @param params 入参
     * @param callback 回调接口
     */
    public static void getClass(int id,RequestParams params,ResponseCallback callback){
        RequestMode.getRequest("https://www.hihia.top/android-back-end-api/classlist/"+id,params,callback,new TypeToken<List<Course>>(){}.getType());
    }


}
