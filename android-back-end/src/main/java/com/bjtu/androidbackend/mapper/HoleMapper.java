package com.bjtu.androidbackend.mapper;


import com.bjtu.androidbackend.model.Hole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface HoleMapper {

    @Insert("insert into hole (content, time) values (#{content}, #{time})")
    public void addHole(Hole hole);

    @Select("select * from hole limit #{id}, 10")
    public List<Hole> holeRefresh(Integer id);

    @Select("select * from hole limit #{id1}, #{id2}")
    public List<Hole> holeLoadMore(Integer id1, Integer id2);

    @Select("select * from hole order by id desc limit 10")
    public List<Hole> holeLatest10();
}
