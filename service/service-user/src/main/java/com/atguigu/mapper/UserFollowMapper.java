package com.atguigu.mapper;

import base.BaseMapper;
import com.atguigu.entity.UserFollow;
import com.atguigu.entity.UserInfo;
import com.atguigu.entity.vo.UserFollowVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

public interface UserFollowMapper extends BaseMapper<UserFollow> {

    UserFollow getByUseridAndHouseId(@Param("userId") Long userId, @Param("houseId") Long houseId,@Param("isDeleted") Integer isDeleted);

    Page<UserFollowVo> findPageList(Long id);
}
