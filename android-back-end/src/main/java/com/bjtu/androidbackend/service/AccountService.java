package com.bjtu.androidbackend.service;

import com.bjtu.androidbackend.model.Account;

import java.util.List;

public interface AccountService {

    public List<Account> loginByEmail(String EmailOrName);

    public List<Account> loginByNickname(String nickname);

    public void register(Account account);

    public void forget(Account account);

    public String getAvatarByNickname(String nickname);

    public void changeAvatar(Account account);

    public void changeBackground(Account account);

    public void newToOld(Integer id);
}
