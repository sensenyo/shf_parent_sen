package com.atguigu.service.impl;

import base.BaseMapper;
import base.BaseServiceImpl;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.entity.HouseBroker;
import com.atguigu.mapper.HouseBrokerMapper;
import com.atguigu.service.HouseBrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = HouseBrokerService.class)
@Transactional
public class HouseBrokerServiceImpl extends BaseServiceImpl<HouseBroker> implements HouseBrokerService {
    @Autowired
    private HouseBrokerMapper houseBrokerMapper;
    @Override
    public BaseMapper<HouseBroker> getMapper() {
        return houseBrokerMapper;
    }

    @Override
    public List<HouseBroker> getByHouseId(Long houseId) {
        return houseBrokerMapper.getByHouseId(houseId);
    }

    @Override
    public HouseBroker getByBrokerId(Long brokerId) {
        return houseBrokerMapper.getByBrokerId(brokerId);
    }
}
