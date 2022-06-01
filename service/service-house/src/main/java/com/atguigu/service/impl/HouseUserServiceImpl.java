package com.atguigu.service.impl;

import base.BaseMapper;
import base.BaseServiceImpl;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.entity.HouseUser;
import com.atguigu.mapper.HouseUserMapper;
import com.atguigu.service.HouseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = HouseUserService.class)
@Transactional
public class HouseUserServiceImpl extends BaseServiceImpl<HouseUser> implements HouseUserService {
    @Autowired
    private HouseUserMapper houseUserMapper;
    @Override
    public BaseMapper<HouseUser> getMapper() {
        return houseUserMapper;
    }

    @Override
    public List<HouseUser> getByHouseId(Long houseId) {
        return houseUserMapper.getByHouseId(houseId);
    }
}
