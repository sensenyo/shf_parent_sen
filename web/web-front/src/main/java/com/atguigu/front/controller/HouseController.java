package com.atguigu.front.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.*;
import com.atguigu.entity.bo.HouseQueryBo;
import com.atguigu.entity.vo.HouseVo;
import com.atguigu.service.*;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import result.Result;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/house")
public class HouseController {

    @Reference
    private HouseService houseService;
    @Reference
    private CommunityService communityService;
    @Reference
    private HouseBrokerService houseBrokerService;
    @Reference
    private HouseImageService houseImageService;
    @Reference
    private HouseUserService houseUserService;
    @Reference
    private UserFollowService userFollowService;

    @ResponseBody
    @RequestMapping("/list/{pageNum}/{pageSize}")
    public Result page(@RequestBody HouseQueryBo houseQueryBo,
                       @PathVariable Integer pageNum,
                       @PathVariable Integer pageSize) {
        PageInfo<HouseVo> pageInfo = houseService.findListPage(houseQueryBo, pageNum, pageSize);
        return Result.ok(pageInfo);
    }

    @ResponseBody
    @GetMapping("/info/{id}")
    public Result info(@PathVariable Long id, HttpSession session) {
        UserInfo user = (UserInfo) session.getAttribute("USER");
        House house = houseService.getById(id);
        Community community = communityService.getById(house.getCommunityId());
        List<HouseBroker> brokers = houseBrokerService.getByHouseId(id);
        List<HouseImage> images = houseImageService.getByHouseIdAndType(id, 1);
        List<HouseUser> houseUsers = houseUserService.getByHouseId(id);
        Boolean isFollow = false;

        System.out.println(userFollowService);
        System.out.println(userFollowService);

        if (user != null)
            if (userFollowService.getByUseridAndHouseId(user.getId(), id, 0) != null) {
                isFollow = true;
            }

        Map<String, Object> map = new HashMap<>();
        map.put("house", house);
        map.put("community", community);
        map.put("houseBrokerList", brokers);
        map.put("houseImage1List", images);
        map.put("isFollow", isFollow);
        return Result.ok(map);
    }
}
