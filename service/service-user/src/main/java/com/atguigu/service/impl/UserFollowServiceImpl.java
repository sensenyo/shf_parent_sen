package com.atguigu.service.impl;

import base.BaseMapper;
import base.BaseServiceImpl;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.entity.UserFollow;
import com.atguigu.entity.vo.UserFollowVo;
import com.atguigu.mapper.UserFollowMapper;
import com.atguigu.service.UserFollowService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

@Service(interfaceClass = UserFollowService.class)
public class UserFollowServiceImpl extends BaseServiceImpl<UserFollow> implements UserFollowService {

    @Autowired
    private UserFollowMapper userFollowMapper;

    @Override
    public BaseMapper<UserFollow> getMapper() {
        return userFollowMapper;
    }

    @Override
    public UserFollow getByUseridAndHouseId(Long userId, Long houseId,Integer isDeleted) {
        return userFollowMapper.getByUseridAndHouseId(userId,houseId,isDeleted);
    }

    @Override
    public PageInfo<UserFollowVo> findPageList(Integer pageNum, Integer pageSize, Long id) {
        PageHelper.startPage(pageNum,pageSize);
        Page<UserFollowVo> page = userFollowMapper.findPageList(id);
        return new PageInfo<>(page,10);
    }
}
