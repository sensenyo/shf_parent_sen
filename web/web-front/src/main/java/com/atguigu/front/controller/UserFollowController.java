package com.atguigu.front.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.UserFollow;
import com.atguigu.entity.UserInfo;
import com.atguigu.entity.vo.UserFollowVo;
import com.atguigu.service.UserFollowService;
import com.atguigu.service.UserInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import result.Result;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/userFollow")
public class UserFollowController {

    @Reference
    private UserFollowService userFollowService;

    @RequestMapping("auth/follow/{id}")
    public Result follow(@PathVariable Long id, HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute("USER");

        UserFollow userFollow = userFollowService.getByUseridAndHouseId(userInfo.getId(), id, 1);
        if (userFollow != null)//删除的情况
        {
            //将deleted变为0
            userFollow.setIsDeleted(0);
            userFollowService.update(userFollow);
        }
        else
        {
            UserFollow userFollow1 = new UserFollow();
            userFollow1.setUserId(userInfo.getId());
            userFollow1.setHouseId(id);
            //插入数据
            userFollowService.insert(userFollow1);
        }

        return Result.ok();
    }


    @RequestMapping("/auth/list/{pageNum}/{pageSize}")
    public Result pageList(@PathVariable Integer pageNum,@PathVariable Integer pageSize,
                            HttpSession session)
    {
        UserInfo userInfo = (UserInfo) session.getAttribute("USER");
        PageInfo<UserFollowVo> page = userFollowService.findPageList(pageNum, pageSize, userInfo.getId());
        return Result.ok(page);
    }


    @RequestMapping("/auth/cancelFollow/{id}")
    public Result cancelFollow(@PathVariable Long id)
    {
        UserFollow userFollow = new UserFollow();
        userFollow.setId(id);
        userFollow.setIsDeleted(1);
        userFollowService.update(userFollow);
        return Result.ok();
    }
}

