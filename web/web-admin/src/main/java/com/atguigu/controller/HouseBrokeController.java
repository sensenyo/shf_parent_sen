package com.atguigu.controller;

import base.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.entity.HouseBroker;
import com.atguigu.service.AdminService;
import com.atguigu.service.HouseBrokerService;
import com.atguigu.service.HouseUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/houseBroker")
public class HouseBrokeController extends BaseController {

    @Reference
    private HouseUserService houseUserService;
    @Reference
    private AdminService adminService;
    @Reference
    private HouseBrokerService houseBrokerService;
    private static final String CREATE_PAGE = "houseBroke/create";
    private static final String SHOW_ACTION = "redirect:/house/";
    private static final String EDIT_PAGE = "houseBroke/edit";

    @RequestMapping("/create")
    public String create(HouseBroker houseBroker, Model model)
    {
        List<Admin> adminList = adminService.getList();
        System.out.println(adminList);
        model.addAttribute("houseBroker",houseBroker);
        model.addAttribute("adminList",adminList);
        return CREATE_PAGE;
    }
    @RequestMapping("/save")
    public String save(HouseBroker houseBroker, Model model)
    {
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBrokerService.insert(houseBroker);
        return toSuccessPage(model,"添加经纪人成功~~");
    }

    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable Long houseId,@PathVariable Long id, Model model)
    {
        houseBrokerService.delete(id);
        return SHOW_ACTION+"/"+houseId;
    }

    @RequestMapping("/edit/{id}")
    public String toEditPage(@PathVariable Long id, Model model)
    {
        HouseBroker houseBroker = houseBrokerService.getById(id);
        List<Admin> adminList = adminService.getList();

        model.addAttribute("houseBroker",houseBroker);
        model.addAttribute("adminList",adminList);
        return EDIT_PAGE;
    }

    @RequestMapping("/update")
    public String toEditPage(HouseBroker houseBroker, Model model)
    {
        //根据brokerId获得admin
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBroker.setBrokerName(admin.getName());
        houseBrokerService.update(houseBroker);
        return toSuccessPage(model,"修改成功");
    }


}
