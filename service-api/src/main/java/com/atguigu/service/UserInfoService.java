package com.atguigu.service;

import base.BaseService;
import com.atguigu.entity.UserInfo;

public interface UserInfoService extends BaseService<UserInfo> {
    int insert(UserInfo userInfo);

    UserInfo getByNickName(UserInfo userInfo);

    UserInfo getByPhone(UserInfo userInfo);

    UserInfo getByPhoneAndPassword(UserInfo userInfo);
}
