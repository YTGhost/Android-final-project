package com.bjtu.androidbackend.service;

import com.bjtu.androidbackend.model.Test;

import java.util.List;

public interface TestService {

    public List<Test> login(String username);

    public List<Test> getList();
}
