package com.bjtu.androidbackend.service;

import com.bjtu.androidbackend.model.Hole;

import java.util.List;

public interface HoleService {

    public void addHole(Hole hole);

    public List<Hole> holeRefresh(Integer id);

    public List<Hole> holeLoadMore(Integer id);
}
