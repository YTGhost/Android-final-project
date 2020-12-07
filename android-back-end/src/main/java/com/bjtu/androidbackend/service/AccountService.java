package com.bjtu.androidbackend.service;

import com.bjtu.androidbackend.model.Account;

import java.util.List;

public interface AccountService {

    public List<Account> login(String username);
}
