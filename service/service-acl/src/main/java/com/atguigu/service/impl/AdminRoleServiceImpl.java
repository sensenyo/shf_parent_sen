package com.atguigu.service.impl;

import base.BaseMapper;
import base.BaseServiceImpl;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.entity.AdminRole;
import com.atguigu.mapper.AdminRoleMapper;
import com.atguigu.service.AdminRoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(interfaceClass = AdminRoleService.class)
public class AdminRoleServiceImpl extends BaseServiceImpl<AdminRole> implements AdminRoleService {
    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Override
    public BaseMapper<AdminRole> getMapper() {
        return adminRoleMapper;
    }

    @Override
    public AdminRole getByAdminIdAndRoleId(Long adminId, Long roleId, Integer isDeleted) {
        return adminRoleMapper.getByAdminIdAndRoleId(adminId,roleId,isDeleted);
    }

    @Override
    public void deleteByAdminId(Long adminId) {
        adminRoleMapper.deleteByAdminId(adminId);
    }

    @Override
    public void assignRole(List<Long> roleIds, Long adminId) {
        adminRoleMapper.deleteByAdminId(adminId);
        for (Long roleId : roleIds) {
            AdminRole adminRole = adminRoleMapper.getByAdminIdAndRoleId(adminId, roleId, 1);
            if (adminRole != null) {
                adminRole.setIsDeleted(0);
                adminRoleMapper.update(adminRole);
            } else {
                AdminRole adminRole1 = new AdminRole();
                adminRole1.setAdminId(adminId);
                adminRole1.setRoleId(roleId);
                adminRoleMapper.insert(adminRole1);
            }
        }
    }
}
