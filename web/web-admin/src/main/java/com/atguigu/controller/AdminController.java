package com.atguigu.controller;

import base.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.entity.AdminRole;
import com.atguigu.entity.Role;
import com.atguigu.service.AdminRoleService;
import com.atguigu.service.AdminService;
import com.atguigu.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import util.FileUtil;
import util.QiniuUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    private static final String UPLOAD_SHOW = "admin/upload";
    //分配角色界面
    private static final String ASSIGN_SHOW_PAGE = "admin/assignShow";
    //用户首页
    private static final String ADMIN_INDEX = "admin/index";
    private static final String EDIT_PAGE = "admin/edit";
    @Reference
    private AdminService adminService;
    @Reference
    private RoleService roleService;
    @Reference
    private AdminRoleService adminRoleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * 去用户首页
     *
     * @param filters
     * @param model
     * @return
     */
    @RequestMapping
    public String index(@RequestParam Map filters, Model model) {
        PageInfo page = adminService.findPage(filters);
        model.addAttribute("filters", filters);
        model.addAttribute("page", page);
        return ADMIN_INDEX;
    }

    /**
     * 添加用户
     *
     * @param admin
     * @param model
     * @return
     */
    @PostMapping("/save")
    public String save(Admin admin, Model model) {
        System.out.println(passwordEncoder);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminService.insert(admin);
        return toSuccessPage(model, "添加成功");
    }

    /**
     * 删除用户
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/delete/{id}")
    public String deleteAdmin(@PathVariable Long id, Model model) {
        adminService.delete(id);
        return toSuccessPage(model, "删除成功");
    }


    @RequestMapping("/edit/{id}")
    public String editAdmin(@PathVariable Long id, Model model) {
        Admin admin = adminService.getById(id);
        model.addAttribute("admin", admin);
        return EDIT_PAGE;
    }

    @RequestMapping("/update")
    public String updateAdmin(Admin admin, Model model) {
        adminService.update(admin);
        return toSuccessPage(model, "修改成功");
    }

    @RequestMapping("uploadShow/{id}")
    public String uploadShow(@PathVariable Long id, Model model) {
        model.addAttribute("id", id);
        return UPLOAD_SHOW;
    }

    @RequestMapping("upload/{id}")
    public String upload(@RequestParam("file") MultipartFile file, @PathVariable Long id, Model model) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String uuidName = FileUtil.getUUIDName(originalFilename);

        QiniuUtils.upload2Qiniu(file.getBytes(), uuidName);
        Admin admin = new Admin();
        admin.setHeadUrl(QiniuUtils.getUrl(uuidName));
        admin.setId(id);
        adminService.update(admin);

        return toSuccessPage(model, "上传头像成功~");
    }

    /**
     * 分配角色界面
     */
    @RequestMapping("/assignShow/{id}")
    public String assignShow(@PathVariable Long id, Model model) {
        List<Role> assignRoleList = roleService.getRoleByAdminId(id);
        List<Role> unAssignRoleList = roleService.getNoRoleByAdminId(id);

        model.addAttribute("adminId",id);
        model.addAttribute("assignRoleList", assignRoleList);
        model.addAttribute("unAssignRoleList", unAssignRoleList);

        return ASSIGN_SHOW_PAGE;
    }



    /**
     * 分配角色功能
     */
    @RequestMapping("/assignRole")
    public String assignRole(@RequestParam("roleIds") List<Long> roleIds,
                             @RequestParam("adminId") Long adminId,Model model) {
        adminRoleService.assignRole(roleIds,adminId);
        return toSuccessPage(model,"添加成功");
    }
}

