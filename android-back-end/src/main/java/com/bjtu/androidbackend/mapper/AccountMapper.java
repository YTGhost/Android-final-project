package com.bjtu.androidbackend.mapper;

import com.bjtu.androidbackend.model.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface AccountMapper {

    @Select("select * from account where email = #{Email}")
    public List<Account> loginByEmail(String Email);

    @Select("select * from account where nickname = #{nickname}")
    public List<Account> loginByNickname(String nickname);

    @Insert("insert into account (nickname, email, password, avatarUrl, backgroundUrl, isNew) values (#{nickname}, #{email}, #{password}, #{avatarUrl}, #{backgroundUrl}, #{isNew})")
    public void register(Account account);

    @Update("update account set password=#{password} where email=#{email}")
    public void forget(Account account);

    @Select("select avatarUrl from account where nickname = #{nickname}")
    public String getAvatarByNickname(String nickname);

    @Update("update account set avatarUrl = #{avatarUrl} where id = #{id}")
    public void changeAvatar(Account account);

    @Update("update account set backgroundUrl = #{backgroundUrl} where id = #{id}")
    public void changeBackground(Account account);

    @Update("update account set isNew = 0 where id = #{id}")
    public void newToOld(Integer id);
}
