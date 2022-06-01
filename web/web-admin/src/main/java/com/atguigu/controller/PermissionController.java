package com.atguigu.controller;

import base.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Permission;
import com.atguigu.service.PermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/permission")
public class PermissionController extends BaseController {
    private static final String CREATE_PAGE = "permission/create";

    private static final String INDEX_PAGE = "redirect:/permission";
    private static final String EDIT_PAGE = "permission/edit";
    @Reference
    private PermissionService permissionService;

    private static final String PERMISSION_INDEX_PAGE = "permission/index";

    @RequestMapping
    public String index(Model model)
    {
        List<Permission> list = permissionService.getList();
        model.addAttribute("list",list);
        return PERMISSION_INDEX_PAGE;
    }


    /**
     * 新建界面
     */
    @RequestMapping("/create")
    public String create(Permission permission,Model model){

        model.addAttribute("permission",permission);
        return CREATE_PAGE;
    }

    /**
     * 保存功能
     */
    @RequestMapping("/save")
    public String save(Permission permission,Model model){
        permissionService.insert(permission);
        return toSuccessPage(model,"添加成功");
    }

    /**
     * 删除功能
     */
    @RequestMapping("delete/{id}")
    public String delete(Model model, @PathVariable Long id)
    {
        permissionService.delete(id);
        return INDEX_PAGE;
    }

    /**
     * 修改界面
     */
    @RequestMapping("/edit/{id}")
    public String edit(Model model,@PathVariable Long id)
    {
        Permission permission = permissionService.getById(id);
        model.addAttribute("permission",permission);
        return EDIT_PAGE;
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,Model model,Permission permission)
    {
        permissionService.update(permission);
        return toSuccessPage(model,"修改成功");
    }
}
