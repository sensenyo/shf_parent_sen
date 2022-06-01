package com.atguigu.controller;

import base.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.HouseUser;
import com.atguigu.service.HouseUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/houseUser")
public class HouseUserController extends BaseController {

    @Reference
    private HouseUserService houseUserService;

    private static final String EDIT_PAGE = "houseUser/edit";
    private static final String CREATE_PAGE = "houseUser/create";
    private static final String SHOW_PAGE = "redirect:/house";

    @RequestMapping("/create")
    public String toCreate(HouseUser houseUser, Model model)
    {
        model.addAttribute("houseUser",houseUser);
        return CREATE_PAGE;
    }

    @RequestMapping("/save")
    public String toCreatePage(HouseUser houseUser, Model model)
    {
        houseUserService.insert(houseUser);
        return toSuccessPage(model,"添加成功~~~");
    }

    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable Long houseId, @PathVariable Long id)
    {
        houseUserService.delete(id);
        return SHOW_PAGE+"/"+houseId;
    }

    @RequestMapping("/edit/{id}")
    public String toEditPage(@PathVariable Long id, Model model)
    {
        HouseUser houseUser = houseUserService.getById(id);
        model.addAttribute("houseUser",houseUser);
        return EDIT_PAGE;
    }
    @RequestMapping("/update")
    public String update(HouseUser houseUser,Model model)
    {
        houseUserService.update(houseUser);
        return toSuccessPage(model,"更新成功~~");
    }

}

