package com.atguigu.front.controller;

import base.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Dict;
import com.atguigu.service.DictService;
import org.springframework.web.bind.annotation.*;
import result.Result;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dict")
public class DictController extends BaseController {
    @Reference
    private DictService dictService;

    @GetMapping("/findZnodes")
    public Result findZNodes(@RequestParam(value = "id",defaultValue = "0") Long id)
    {
        List<Map<String, Object>> znodes = dictService.findZnodes(id);
        return Result.ok(znodes);
    }

    /**
     * 根据父类id查找所有子类
     * @return
     */
    @RequestMapping("/findListByParentId/{parentId}")
    public Result findDictListByParentId(@PathVariable Long parentId){
        List<Dict> listByParentId = dictService.findListByParentId(parentId);
        return Result.ok(listByParentId);
    }

    /**
     * 根据父类code查找
     */
    @RequestMapping("/findListByDictCode/{code}")
    public Result findDictListByParentDictCode(@PathVariable String code)
    {
        List<Dict> dicts = dictService.findDictListByDictCode(code);
        return Result.ok(dicts);
    }

}
