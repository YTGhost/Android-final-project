package com.bjtu.androidbackend.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "用户账户实体类")
public class Account implements Serializable {

    @ApiModelProperty(name = "id", value = "用户Id", required = false)
    private Integer id;

    @ApiModelProperty(name = "nickname", value = "用户名", required = false)
    private String nickname;

    @ApiModelProperty(name = "email", value = "邮箱", required = true)
    private String email;

    @ApiModelProperty(name = "password", value = "密码", required = true)
    private String password;

    @ApiModelProperty(name = "code", value = "校验码", required = false)
    private String code;

    @ApiModelProperty(name = "avatarUrl", value = "头像地址", required = false)
    private String avatarUrl;

    @ApiModelProperty(name = "backgroundUrl", value = "背景图地址", required = false)
    private String backgroundUrl;

    // 1为新用户，0为老用户
    @ApiModelProperty(name = "isNew", value = "是否是新用户", required = false)
    private Integer isNew;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", code='" + code + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", backgroundUrl='" + backgroundUrl + '\'' +
                ", isNew=" + isNew +
                '}';
    }
}
