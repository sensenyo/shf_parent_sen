package com.atguigu.mapper;

import base.BaseMapper;
import com.atguigu.entity.Admin;

public interface AdminMapper extends BaseMapper<Admin> {
   Admin getByUsername(String username);
}
