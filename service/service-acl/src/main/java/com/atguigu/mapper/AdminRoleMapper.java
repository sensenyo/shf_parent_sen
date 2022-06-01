package com.atguigu.mapper;

import base.BaseMapper;
import com.atguigu.entity.AdminRole;
import org.apache.ibatis.annotations.Param;

public interface AdminRoleMapper extends BaseMapper<AdminRole> {
    //获取AdminRole对象 根据adminId和roleId
    AdminRole getByAdminIdAndRoleId(@Param("adminId") Long adminId,
                                    @Param("roleId") Long roleId,
                                    @Param("isDeleted") Integer isDeleted);

    void deleteByAdminId(Long adminId);
}
