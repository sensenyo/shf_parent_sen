package com.atguigu.mapper;

import base.BaseMapper;
import com.atguigu.entity.House;
import com.atguigu.entity.HouseImage;
import com.atguigu.entity.bo.HouseQueryBo;
import com.atguigu.entity.vo.HouseVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HouseMapper extends BaseMapper<House> {

    void publish(@Param("id") Long id, @Param("status") Integer status);

    Page<HouseVo> findListPage(HouseQueryBo houseQueryBo);
}
