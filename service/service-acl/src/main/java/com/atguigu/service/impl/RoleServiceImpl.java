package com.atguigu.service.impl;

import base.BaseMapper;
import base.BaseServiceImpl;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.entity.RolePermission;
import com.atguigu.mapper.RoleMapper;
import com.atguigu.entity.Role;
import com.atguigu.mapper.RolePermissionMapper;
import com.atguigu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service(interfaceClass = RoleService.class)
@Transactional(propagation = Propagation.REQUIRED)
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public BaseMapper<Role> getMapper() {
        return roleMapper;
    }


    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Role> getAllRole() {
        return roleMapper.getAllRole();
    }

    @Override
    public List<Role> getRoleByAdminId(Long adminId) {
        return roleMapper.getRoleByAdminId(adminId);
    }

    @Override
    public List<Role> getNoRoleByAdminId(Long adminId) {
        return roleMapper.getNoRoleByAdminId(adminId);
    }

    @Override
    public void assignPermission(Long roleId, List<Long> permissionIds) {
        //根据roleId获得所有的permission
        List<Long> permissionIdsByRoleId = rolePermissionMapper.getPermissionIdsByRoleId(roleId);
        //与新的permissionId进行对比
        List<Long> deletePermissionIds = permissionIdsByRoleId.stream()
                .filter(item -> !permissionIds.contains(item)).collect(Collectors.toList());

        if (deletePermissionIds != null && deletePermissionIds.size() != 0) {
            rolePermissionMapper.removeRolePermission(roleId, deletePermissionIds);
        }

        for (Long permissionId : permissionIds) {
            RolePermission rolePermission = rolePermissionMapper.findByRoleIdAndPermissionId(roleId, permissionId);
            if (rolePermission != null) {
                if (rolePermission.getIsDeleted() == 1) {
                    rolePermission.setIsDeleted(0);
                    rolePermissionMapper.update(rolePermission);
                }
            } else {
                RolePermission rolePermission1 = new RolePermission();
                rolePermission1.setRoleId(roleId);
                rolePermission1.setPermissionId(permissionId);
                rolePermissionMapper.insert(rolePermission1);
            }
        }
    }


    //如果不存在就判断之前是否存在
}
