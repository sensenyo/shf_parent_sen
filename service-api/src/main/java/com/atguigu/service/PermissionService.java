package com.atguigu.service;

import base.BaseService;
import com.atguigu.entity.Permission;

import java.util.List;
import java.util.Map;

public interface PermissionService extends BaseService<Permission> {

    List<Map<String, Object>> assignShow(Long roleId);

    List<Permission> findMenuPermissionByAdminId(Long adminId);

    List<Permission> getList();

    List<String> findCodePermissionListByAdminId(Long adminId);
}
