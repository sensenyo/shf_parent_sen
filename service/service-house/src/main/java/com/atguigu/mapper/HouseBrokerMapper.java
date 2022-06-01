package com.atguigu.mapper;

import base.BaseMapper;
import com.atguigu.entity.HouseBroker;

import java.util.List;

public interface HouseBrokerMapper extends BaseMapper<HouseBroker> {
    public List<HouseBroker> getByHouseId(Long houseId);

    HouseBroker getByBrokerId(Long brokerId);
}
