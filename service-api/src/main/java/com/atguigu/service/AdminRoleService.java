package com.atguigu.service;

import base.BaseService;
import com.atguigu.entity.AdminRole;

import java.util.List;

public interface AdminRoleService extends BaseService<AdminRole> {
    //获取AdminRole对象 根据adminId和roleId
    AdminRole getByAdminIdAndRoleId(Long adminId,Long roleId,Integer isDeleted);

    //删除所有 根据AdminId
    void deleteByAdminId(Long adminId);

    void assignRole(List<Long> roleIds, Long adminId);
}
