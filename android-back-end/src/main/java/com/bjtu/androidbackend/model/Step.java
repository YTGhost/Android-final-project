package com.bjtu.androidbackend.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "用户计步模块")
public class Step implements Serializable{
    @ApiModelProperty(name = "nickname", value = "用户名", required = false)
    private String nickname;

    @ApiModelProperty(name = "steps",value="步数",required = false)
    private int steps;

    @ApiModelProperty(name = "avatarUrl", value = "头像", required = false)
    private String avatarUrl;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return "Step{" +
                "nickname='" + nickname + '\'' +
                ", steps=" + steps +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}
