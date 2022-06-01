package com.atguigu.controller;

import base.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Community;
import com.atguigu.entity.Dict;
import com.atguigu.service.CommunityService;
import com.atguigu.service.DictService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/community")
public class CommunityController extends BaseController {
    @Reference
    private CommunityService communityService;
    @Reference
    private DictService dictService;

    private static final String CREATE_PAGE = "community/create";
    private static final String INDEX_PAGE = "community/index";
    private static final String EDIT_PAGE = "community/edit";
    @RequestMapping
    public String index(@RequestParam Map filters, Model model)
    {
        if(!filters.containsKey("plateId"))
        {
            filters.put("plateId","");
        }
        if(!filters.containsKey("areaId"))
        {
            filters.put("areaId","");
        }
        PageInfo<Community> page = communityService.findPage(filters);
        List<Dict> areaList = dictService.findDictListByDictCode("beijing");
        model.addAttribute("page",page);
        model.addAttribute("filters",filters);
        model.addAttribute("areaList",areaList);
        System.out.println(areaList);
        System.out.println(page);
        return INDEX_PAGE;
    }

    /**
     * 新建房源界面
     * @return
     */
    @RequestMapping("/create")
    public String toCreatePage(Model model)
    {
        List<Dict> areaList = dictService.findDictListByDictCode("beijing");
        model.addAttribute("areaList",areaList);
        return CREATE_PAGE;
    }

    /**
     * 保存按钮
     * @return
     */
    @RequestMapping("/save")
    public String save(Community community,Model model)
    {
        communityService.insert(community);
        return toSuccessPage(model,"新建成功!!");
    }

    /**
     * 删除功能
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model)
    {
        communityService.delete(id);
        return toSuccessPage(model,"删除成功哦~~");
    }

    /**
     * 修改界面
     * @return
     */
    @RequestMapping("/edit/{id}")
    public String toEditPage(@PathVariable Long id,Model model)
    {
        List<Dict> areaList = dictService.findDictListByDictCode("beijing");
        Community community = communityService.getById(id);
        model.addAttribute("community",community);
        model.addAttribute("areaList",areaList);
        return EDIT_PAGE;
    }

    @RequestMapping("/update")
    public String update(Community community,Model model)
    {
        communityService.update(community);
        return toSuccessPage(model,"修改成功!");
    }
}
