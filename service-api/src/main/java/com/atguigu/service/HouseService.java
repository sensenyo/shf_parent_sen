package com.atguigu.service;

import base.BaseService;
import com.atguigu.entity.House;
import com.atguigu.entity.bo.HouseQueryBo;
import com.atguigu.entity.vo.HouseVo;
import com.github.pagehelper.PageInfo;

public interface HouseService extends BaseService<House> {

    /**
     * 修改状态
     *
     * @param id
     * @param status
     */
    void publish(Long id, Integer status);

    PageInfo<HouseVo> findListPage(HouseQueryBo houseQueryBo,Integer pageNum,Integer pageSize);
}
