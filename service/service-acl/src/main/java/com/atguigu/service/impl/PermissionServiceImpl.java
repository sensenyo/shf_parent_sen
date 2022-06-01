package com.atguigu.service.impl;

import base.BaseMapper;
import base.BaseServiceImpl;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.entity.Permission;
import com.atguigu.helper.MyHelper;
import com.atguigu.mapper.PermissionMapper;
import com.atguigu.mapper.RolePermissionMapper;
import com.atguigu.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service(interfaceClass = PermissionService.class)
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Override
    public BaseMapper<Permission> getMapper() {
        return permissionMapper;
    }

    @Override
    public List<Map<String, Object>> assignShow(Long roleId) {
        //获得全部的Permission对象
        List<Permission> permissionMapperList = permissionMapper.getList();
        //获得roleId对应的PermissionId
        List<Long> permissionIds = rolePermissionMapper.getPermissionIdsByRoleId(roleId);
        //查看对应的或者没有的
        List<Map<String, Object>> list = new ArrayList<>();
        for (Permission permission : permissionMapperList) {
            Map map = new HashMap();
            map.put("id",permission.getId());
            map.put("pId",permission.getParentId());
            map.put("name",permission.getName());
            if(permissionIds.contains(permission.getId()))
            {
                map.put("checked",true);
            }
            else
            {
                map.put("checked",false);
            }
            list.add(map);
        }
        return list;
    }

    @Override
    public List<Permission> findMenuPermissionByAdminId(Long adminId) {
        List<Permission> permissionList = null;
        if(adminId == 1)
        {
            permissionList = permissionMapper.getList();
        }
        else
        {
            permissionList = permissionMapper.findPermissionListByAdminId(adminId);
        }

        return MyHelper.build(permissionList);
    }



    public List<Permission> getList()
    {
        List<Permission> list = permissionMapper.getList();
        return MyHelper.build(list);
    }


    @Override
    public List<String> findCodePermissionListByAdminId(Long adminId) {
        if(adminId == 1)
        {
            return permissionMapper.getAllCode();
        }
        return permissionMapper.getCodeByAdminId(adminId);
    }
}
