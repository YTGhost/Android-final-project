package com.bjtu.androidbackend.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "系统测试实体类")
public class Test implements Serializable {

    @ApiModelProperty(name = "id", value = "测试Id", required = false)
    private Integer id;

    @ApiModelProperty(name = "username", value = "测试账号用户名", required = true)
    private String username;

    @ApiModelProperty(name = "password", value = "测试账号密码", required = true)
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
