package com.atguigu.service;

import base.BaseService;
import com.atguigu.entity.Dict;

import java.util.List;
import java.util.Map;

public interface DictService extends BaseService<Dict> {

    List<Map<String,Object>> findZnodes(Long id);

    /**
     * 根据父类的code查找所有的子类
     * @param dictCode
     * @return
     */
    List<Dict> findDictListByDictCode(String dictCode);

    /**
     * 根据父类id查找所有子类
     * @param parentId
     * @return
     */
    List<Dict> findListByParentId(Long parentId);
}
