package com.bjtu.androidbackend.mapper;

import com.bjtu.androidbackend.model.Test;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TestMapper {

    @Select("select password from test where username = #{username}")
    public List<Test> login(String username);

    @Select("select * from test")
    public List<Test> getList();
}
