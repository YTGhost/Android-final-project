package com.bjtu.androidbackend.mapper;

import com.bjtu.androidbackend.model.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface AccountMapper {

    @Select("select * from account where email = #{Email}")
    public List<Account> loginByEmail(String Email);

    @Select("select * from account where nickname = #{nickname}")
    public List<Account> loginByNickname(String nickname);

    @Insert("insert into account (nickname, email, password) values (#{nickname}, #{email}, #{password})")
    public void register(Account account);
}
