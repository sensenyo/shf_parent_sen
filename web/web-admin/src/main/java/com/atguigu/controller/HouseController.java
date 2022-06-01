package com.atguigu.controller;

import base.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.en.HouseStatus;
import com.atguigu.entity.Community;
import com.atguigu.entity.Dict;
import com.atguigu.entity.House;
import com.atguigu.entity.HouseUser;
import com.atguigu.service.*;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@RequestMapping("/house")
@Controller
public class HouseController extends BaseController {

    @Reference
    private HouseService houseService;
    @Reference
    private CommunityService communityService;
    @Reference
    private DictService dictService;
    @Reference
    private HouseImageService houseImageService;
    @Reference
    private HouseBrokerService houseBrokerService;
    @Reference
    private HouseUserService houseUserService;

    private static final String CREATE_PAGE = "house/create";
    private static final String INDEX_PAGE = "house/index";
    private static final String EDIT_PAGE = "house/edit";
    private static final String LIST_ACTION = "redirect:/house";
    private static final String SHOW_PAGE = "house/show";

    @RequestMapping
    public String index(@RequestParam Map filters, Model model) {
        PageInfo<House> page = houseService.findPage(filters);
        System.out.println(page);
        model.addAttribute("filters", filters);
        model.addAttribute("page", page);
        condition(model);

        return INDEX_PAGE;
    }

    /**
     * 新建窗口
     *
     * @param
     * @param model
     * @return
     */
    @RequestMapping("/create")
    public String toCreatePage(Model model) {
        condition(model);
        return CREATE_PAGE;
    }

    /**
     * 保存新建房源
     *
     * @param
     * @param model
     * @return
     */
    @RequestMapping("/save")
    public String save(House house, Model model) {
        house.setStatus(HouseStatus.UNPUBLISHED.getCode());
        houseService.insert(house);
        return toSuccessPage(model, "保存成功哦~~");
    }

    /**
     * 逻辑删除
     *
     * @param
     * @param model
     * @return
     */
    @RequestMapping("/delete/{id}")
    public String save(@PathVariable Long id, Model model) {
        houseService.delete(id);
        return toSuccessPage(model, "删除成功哦~~");
    }

    /**
     * 修改界面
     *
     * @param
     * @param model
     * @return
     */
    @RequestMapping("/edit/{id}")
    public String toEditPage(@PathVariable Long id, Model model) {
        List<Community> communityList = communityService.getList();
        model.addAttribute("communityList", communityList);
        House house = houseService.getById(id);
        model.addAttribute("house", house);
        return EDIT_PAGE;
    }

    /**
     * 修改功能
     *
     * @param
     * @param model
     * @return
     */
    @RequestMapping("/update")
    public String update(House house, Model model) {
        houseService.update(house);
        return toSuccessPage(model, "修改成功");
    }

//    update

    /**
     * 搜索条件
     *
     * @param model
     */
    private void condition(Model model) {
        List<Community> communityList = communityService.getList();
        List<Dict> houseTypeList = dictService.findDictListByDictCode("houseType");
        List<Dict> floorList = dictService.findDictListByDictCode("floor");
        List<Dict> buildStructureList = dictService.findDictListByDictCode("buildStructure");
        List<Dict> decorationList = dictService.findDictListByDictCode("decoration");
        List<Dict> directionList = dictService.findDictListByDictCode("direction");
        List<Dict> houseUseList = dictService.findDictListByDictCode("houseUse");

        model.addAttribute("communityList", communityList);
        model.addAttribute("houseTypeList", houseTypeList);
        model.addAttribute("floorList", floorList);
        model.addAttribute("buildStructureList", buildStructureList);
        model.addAttribute("decorationList", decorationList);
        model.addAttribute("directionList", directionList);
        model.addAttribute("houseUseList", houseUseList);
    }

    @RequestMapping("/publish/{id}/{status}")
    public String publish(@PathVariable Long id, @PathVariable Integer status) {
        houseService.publish(id,status);
        return LIST_ACTION;
    }

    @RequestMapping("/{id}")
    public String publish(@PathVariable Long id,Model model) {
        House house = houseService.getById(id);
        Community community = communityService.getById(house.getCommunityId());

        model.addAttribute("community",community);
        model.addAttribute("house",house);
        model.addAttribute("houseImage1List",houseImageService.getByHouseIdAndType(id, 1));
        model.addAttribute("houseImage2List",houseImageService.getByHouseIdAndType(id, 2));
        model.addAttribute("houseBrokerList",houseBrokerService.getByHouseId(id));
        model.addAttribute("houseUserList",houseUserService.getByHouseId(id));
        List<HouseUser> byHouseId = houseUserService.getByHouseId(id);
        System.out.println(byHouseId);
        return SHOW_PAGE;
    }
}
