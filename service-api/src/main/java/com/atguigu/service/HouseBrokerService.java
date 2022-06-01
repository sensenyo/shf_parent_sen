package com.atguigu.service;

import base.BaseService;
import com.atguigu.entity.HouseBroker;

import java.util.List;

public interface HouseBrokerService extends BaseService<HouseBroker> {

    List<HouseBroker> getByHouseId(Long houseId);

    HouseBroker getByBrokerId(Long brokerId);
}
