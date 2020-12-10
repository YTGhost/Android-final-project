package com.bjtu.androidbackend.service.impl;

import com.bjtu.androidbackend.mapper.TestMapper;
import com.bjtu.androidbackend.model.Test;
import com.bjtu.androidbackend.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("testService")
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Override
    public List<Test> login(String username) {
        return testMapper.login(username);
    }

    @Override
    public List<Test> getList() {
        return testMapper.getList();
    }
}
