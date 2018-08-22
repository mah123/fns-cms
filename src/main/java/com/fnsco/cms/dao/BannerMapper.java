package com.fnsco.cms.dao;

import com.fnsco.cms.model.Banner;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BannerMapper {


    int deleteByPrimaryKey(Integer bannerId);

    int insert(Banner record);

    int insertSelective(Banner record);


    Banner selectByPrimaryKey(Integer bannerId);

    int updateByPrimaryKeySelective(Banner record);

    int updateByPrimaryKey(Banner record);

    //逻辑删除
    Integer deleteBanner(Integer bannerId);

    List<Banner> selectAll();
}