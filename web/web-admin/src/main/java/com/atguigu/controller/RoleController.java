package com.atguigu.controller;

import base.BaseController;
import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Role;
import com.atguigu.service.PermissionService;
import com.atguigu.service.RoleService;
import com.github.pagehelper.PageInfo;
import com.qiniu.util.Json;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import result.Result;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
    private static final String ASSIGN_SHOW_PAGE = "role/assignShow";
    @Reference
    private RoleService roleService;
    @Reference
    private PermissionService permissionService;

    //首页
    private static final String ROLE_INDEX = "role/index";
    //修改界面
    private static final String ROLE_EDIT = "role/edit";

    /**
     * 角色管理界面
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyAuthority('role.show')")
    @RequestMapping
    public String index(@RequestParam Map filters, Model model) {
        System.out.println(filters);
        PageInfo<Role> page = roleService.findPage(filters);
        model.addAttribute("page", page);
        model.addAttribute("filters", filters);
        return ROLE_INDEX;
    }

    /**
     * 添加用户操作
     *
     * @param role
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyAuthority('role.create')")
    @RequestMapping("/save")
    public String saveRole(Role role, Model model) {
        roleService.insert(role);
        return toSuccessPage(model, "添加成功");
    }

    /**
     * 到修改界面
     */
    @PreAuthorize("hasAnyAuthority('role.edit')")
    @GetMapping("/edit/{id}")
    public String toEditRolePage(@PathVariable Long id, Model model) {
        Role role = roleService.getById(id);
        model.addAttribute("role", role);
        return ROLE_EDIT;
    }

    /**
     * 修改操作
     *
     * @param role
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyAuthority('role.edit')")
    @RequestMapping("/update")
    public String updateRole(Role role, Model model) {
        int flag = roleService.update(role);
        return toSuccessPage(model, "修改成功");
    }

    /**
     * 删除操作
     */
    @PreAuthorize("hasAnyAuthority('role.delete')")
    @GetMapping("/delete/{id}")
    public String deleteRole(@PathVariable Long id, Model model) {
        roleService.delete(id);
        return toSuccessPage(model, "删除成功");
    }

    /**
     * 分配权限界面
     */
    @PreAuthorize("hasAnyAuthority('role.assgin')")
    @RequestMapping("/assignShow/{roleId}")
    public String assignShow(@PathVariable Long roleId, Model model)
    {
        List<Map<String, Object>> list = permissionService.assignShow(roleId);
        model.addAttribute("roleId",roleId);
        model.addAttribute("zNodes",JSONUtils.toJSONString(list));
        return ASSIGN_SHOW_PAGE;
    }
    /**
     * 分配权限界面
     */
    @PreAuthorize("hasAnyAuthority('role.assgin')")
    @RequestMapping("/assignPermission")
    public String assignShow(@RequestParam("roleId") Long roleId,
                             @RequestParam("permissionIds") List<Long> permissionIds
                            ,Model model)
    {
        roleService.assignPermission(roleId,permissionIds);
        return toSuccessPage(model,"分配权限成功");
    }
//    assignPermission
}
