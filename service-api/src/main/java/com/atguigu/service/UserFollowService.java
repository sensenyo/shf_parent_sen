package com.atguigu.service;

import base.BaseService;
import com.atguigu.entity.UserFollow;
import com.atguigu.entity.vo.UserFollowVo;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;


public interface UserFollowService extends BaseService<UserFollow> {
    UserFollow getByUseridAndHouseId(Long userId, Long houseId , Integer isDeleted);

    PageInfo<UserFollowVo> findPageList(Integer pageNum, Integer pageSize, Long id);
}
