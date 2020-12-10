package com.bjtu.androidbackend.service.impl;

import com.bjtu.androidbackend.mapper.AccountMapper;
import com.bjtu.androidbackend.model.Account;
import com.bjtu.androidbackend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public List<Account> loginByEmail(String email) {
        return accountMapper.loginByEmail(email);
    }

    @Override
    public List<Account> loginByNickname(String nickname) {
        return accountMapper.loginByNickname(nickname);
    }

    @Override
    public void register(Account account) {
        accountMapper.register(account);
    }
}