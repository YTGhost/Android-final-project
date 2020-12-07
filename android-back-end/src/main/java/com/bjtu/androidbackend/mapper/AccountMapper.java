package com.bjtu.androidbackend.mapper;

import com.bjtu.androidbackend.model.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface AccountMapper {

    @Select("select password from account where username = #{username}")
    public List<Account> login(String username);
}
