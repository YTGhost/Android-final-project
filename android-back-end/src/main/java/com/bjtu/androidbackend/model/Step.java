package com.bjtu.androidbackend.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户计步模块")
public class Step {
    @ApiModelProperty(name = "nickname", value = "用户名", required = false)
    private String nickname;

    @ApiModelProperty(name = "steps",value="步数",required = false)
    private int steps;

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

    @Override
    public String toString() {
        return "Step{" +
                "nickname='" + nickname + '\'' +
                ", steps=" + steps +
                '}';
    }
}
