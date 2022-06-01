package com.atguigu.service;

import base.BaseService;
import com.atguigu.entity.HouseUser;

import java.util.List;

public interface HouseUserService extends BaseService<HouseUser> {
    List<HouseUser> getByHouseId(Long houseId);
}
