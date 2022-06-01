package com.atguigu.service.impl;

import base.BaseMapper;
import base.BaseService;
import base.BaseServiceImpl;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.entity.UserInfo;
import com.atguigu.mapper.UserInfoMapper;
import com.atguigu.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import util.MD5;

@Service(interfaceClass = UserInfoService.class)
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo> implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public BaseMapper<UserInfo> getMapper() {
        return userInfoMapper;
    }

    @Override
    public int insert(UserInfo userInfo)
    {
        //加密
        userInfo.setPassword(MD5.encrypt(userInfo.getPassword()));
        return userInfoMapper.insert(userInfo);
    }

    @Override
    public UserInfo getByNickName(UserInfo userInfo) {
        return userInfoMapper.getByNickName(userInfo);
    }

    @Override
    public UserInfo getByPhone(UserInfo userInfo) {
        return userInfoMapper.getByPhone(userInfo);
    }

    @Override
    public UserInfo getByPhoneAndPassword(UserInfo userInfo) {
        //加密
        userInfo.setPassword(MD5.encrypt(userInfo.getPassword()));
        return userInfoMapper.getByPhoneAndPassword(userInfo);
    }
}
