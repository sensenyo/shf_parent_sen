package com.atguigu.service;

import base.BaseService;
import com.atguigu.entity.HouseImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HouseImageService extends BaseService<HouseImage> {

    List<HouseImage> getByHouseIdAndType(Long houseId ,Integer type);

}
