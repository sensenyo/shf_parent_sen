package com.atguigu.mapper;

import base.BaseMapper;
import com.atguigu.entity.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {

   List<Long> getIdListByRoleId(Long roleId);

    List<Permission> findPermissionListByAdminId(Long adminId);

    List<String> getAllCode();

    List<String> getCodeByAdminId(Long adminId);
}
