package com.atguigu.mapper;

import base.BaseMapper;
import com.atguigu.entity.HouseImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HouseImageMapper extends BaseMapper<HouseImage> {

    List<HouseImage> getByHouseIdAndType(@Param("houseId") Long houseId,@Param("type") Integer type);

}
