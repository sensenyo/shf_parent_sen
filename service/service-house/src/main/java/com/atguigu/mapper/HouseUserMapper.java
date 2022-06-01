package com.atguigu.mapper;

import base.BaseMapper;
import com.atguigu.entity.HouseUser;

import java.util.List;

public interface HouseUserMapper extends BaseMapper<HouseUser> {
     List<HouseUser> getByHouseId(Long houseId);
}