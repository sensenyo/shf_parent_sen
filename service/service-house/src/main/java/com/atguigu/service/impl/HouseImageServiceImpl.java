package com.atguigu.service.impl;

import base.BaseMapper;
import base.BaseServiceImpl;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.entity.House;
import com.atguigu.entity.HouseImage;
import com.atguigu.mapper.HouseImageMapper;
import com.atguigu.mapper.HouseMapper;
import com.atguigu.service.HouseImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service(interfaceClass = HouseImageService.class)
public class HouseImageServiceImpl extends BaseServiceImpl<HouseImage> implements HouseImageService {
    @Autowired
    private HouseImageMapper houseImageMapper;
    @Override
    public BaseMapper<HouseImage> getMapper() {
        return houseImageMapper;
    }

    @Override
    public List<HouseImage> getByHouseIdAndType(Long houseId, Integer type) {
        return houseImageMapper.getByHouseIdAndType(houseId,type);
    }
}
