package com.atguigu.service;

import base.BaseService;
import com.atguigu.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleService extends BaseService<Role> {
   //获取全部角色
   List<Role> getAllRole();

   //根据用户ID获取所有的角色
   List<Role> getRoleByAdminId(Long adminId);


   //根据用户ID获取所有不是的角色
   List<Role> getNoRoleByAdminId(Long adminId);

    void assignPermission(Long roleId, List<Long> permissionIds);
}
