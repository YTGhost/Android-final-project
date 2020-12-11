package com.bjtu.androidbackend.service.impl;

import com.bjtu.androidbackend.mapper.HoleMapper;
import com.bjtu.androidbackend.model.Hole;
import com.bjtu.androidbackend.service.HoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service("holeService")
public class HoleServiceImpl implements HoleService {

    @Autowired
    HoleMapper holeMapper;

    @Override
    public void addHole(Hole hole) {
        holeMapper.addHole(hole);
    }

    @Override
    public List<Hole> holeRefresh(Integer id) {
        if(id == -1) {
            List<Hole> list = holeMapper.holeLatest10();
            Collections.reverse(list);
            return list;
        }
        return holeMapper.holeRefresh(id);
    }

    @Override
    public List<Hole> holeLoadMore(Integer id) {
        if(id <= 10) {
            return holeMapper.holeLoadMore(0, id-1);
        } else {
            return holeMapper.holeLoadMore(id-11, 10);
        }
    }
}
