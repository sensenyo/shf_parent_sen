package com.atguigu.service;

import base.BaseService;
import com.atguigu.entity.Admin;


public interface AdminService extends BaseService<Admin> {
    Admin getByUsername(String username);
}
