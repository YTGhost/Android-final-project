package com.bjtu.campus_information_platform.util.network;

import com.bjtu.campus_information_platform.model.Account;
import com.bjtu.campus_information_platform.model.Hole;
import com.bjtu.campus_information_platform.model.Course;
import com.bjtu.campus_information_platform.model.Step;
import com.bjtu.campus_information_platform.model.Test;
import com.google.gson.reflect.TypeToken;

import java.io.File;
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
     * 发送注册验证码接口
     * @param params 入参
     * @param callback 回调接口
     */
    public static void getRegisterCodeRequest(String email, RequestParams params, ResponseCallback callback) {
        RequestMode.getRequest("https://www.hihia.top/android-back-end-api/account/getRegisterCode/" + email, params, callback, null);
    }

    /**
     * 发送忘记密码验证码接口
     * @param params 入参
     * @param callback 回调接口
     */
    public static void getForgetCodeRequest(String email, RequestParams params, ResponseCallback callback) {
        RequestMode.getRequest("https://www.hihia.top/android-back-end-api/account/getForgetCode/" + email, params, callback, null);
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
        RequestMode.postRequest("https://www.hihia.top/android-back-end-api/step/getStep", params, callback, new TypeToken<List<Step>>(){}.getType());
    }

    /**
     * 修改用户密码
     * @param params 入参
     * @param callback 回调接口
     */
    public static void forgetRequest(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest("https://www.hihia.top/android-back-end-api/account/forget", params, callback, null);
    }


    /**
     * 更新树洞
     * @param params 入参
     * @param callback 回调接口
     */

    public static void getHoleRefreshApi(String id, RequestParams params,ResponseCallback callback){
        RequestMode.getRequest("https://www.hihia.top/android-back-end-api/hole/holeRefresh/" + id, params, callback,new TypeToken<List<Hole>>(){}.getType());
    }

    /**
     * 加载树洞
     * @param params 入参
     * @param callback  回调接口
     */

    public static void getHoleLoadMoreApi(String id, RequestParams params,ResponseCallback callback){
        RequestMode.getRequest("https://www.hihia.top/android-back-end-api/hole/holeLoadMore/" + id,params,callback,new TypeToken<List<Hole>>(){}.getType());
    }

    /**
     * 新增树洞
     * @param params 入参
     * @param callback 回调接口
     */

    public static void postHoleAddHoleApi(RequestParams params,ResponseCallback callback){
        RequestMode.postRequest("https://www.hihia.top/android-back-end-api/hole/addHole",params,callback,null);
    }

    /**
     *查询空闲教室
     * @param params
     * @param file
     * @param callback
     */
    public static void postClassQueryApi(RequestParams params,ResponseCallback callback){
        RequestMode.postRequest("https://www.hihia.top/android-back-end-api/queryClass/freeroom",params,callback,new TypeToken<List<String>>(){}.getType());
    }
  
    /**
     * 图片上传服务器
     * @param params
     * @param file
     * @param callback
     */
    public static void postMultipartApi(RequestParams params, File file, ResponseCallback callback) {
        RequestMode.postMultipart("https://www.hihia.top/android-back-end-api/oss/upload", params, file, callback, null);
    }

    /**
     * 下载图片 Get方式
     * @param params 入参
     * @param imgPath 存储地址
     * @param callback 回调接口
     */
    public static void getImgApi(String url, RequestParams params,String imgPath, ResponseByteCallback callback) {
        RequestMode.getLoadImg(url, params, imgPath, callback);
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

    /**
     * 更新头像
     * @param params 入参
     * @param callback 回调接口
     */
    public static void changeAvatar(RequestParams params, ResponseCallback callback){
        RequestMode.postRequest("https://www.hihia.top/android-back-end-api/account/changeAvatar", params, callback, null);
    }

    /**
     * 更新背景图
     * @param params 入参
     * @param callback 回调接口
     */
    public static void changeBackground(RequestParams params, ResponseCallback callback){
        RequestMode.postRequest("https://www.hihia.top/android-back-end-api/account/changeBackground", params, callback, null);
    }
}
