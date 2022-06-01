package com.atguigu.mapper;

import base.BaseMapper;
import com.atguigu.entity.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    List<Long> getPermissionIdsByRoleId(Long roleId);

    void removeRolePermission(@Param("roleId") Long roleId, @Param("permissionIds") List<Long> permissionIds);

    RolePermission findByRoleIdAndPermissionId(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);
}
