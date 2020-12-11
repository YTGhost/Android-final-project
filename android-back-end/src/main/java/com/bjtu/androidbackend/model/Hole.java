package com.bjtu.androidbackend.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "树洞实体类")
public class Hole implements Serializable {

    @ApiModelProperty(name = "id", value = "树洞Id", required = false)
    private Integer id;

    @ApiModelProperty(name = "content", value = "树洞内容", required = false)
    private String content;

    @ApiModelProperty(name = "time", value = "树洞发布时间", required = false)
    private String time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Hole{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
