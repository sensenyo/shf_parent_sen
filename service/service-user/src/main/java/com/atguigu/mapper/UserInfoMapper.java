package com.atguigu.mapper;

import base.BaseMapper;
import com.atguigu.entity.UserInfo;

public interface UserInfoMapper extends BaseMapper<UserInfo> {

    UserInfo getByNickName(UserInfo userInfo);

    UserInfo getByPhone(UserInfo userInfo);

    UserInfo getByPhoneAndPassword(UserInfo userInfo);
}
