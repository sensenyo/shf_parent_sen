package com.atguigu.mapper;

import base.BaseMapper;
import com.atguigu.entity.Role;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role>  {

    //获取全部角色
    List<Role> getAllRole();

    //根据用户ID获取所有的角色
    List<Role> getRoleByAdminId(Long adminId);


    //根据用户ID获取所有不是的角色
    List<Role> getNoRoleByAdminId(Long adminId);

}
