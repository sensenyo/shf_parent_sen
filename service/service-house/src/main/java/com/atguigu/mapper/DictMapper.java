package com.atguigu.mapper;

import base.BaseMapper;
import com.atguigu.entity.Dict;

import java.util.List;

public interface DictMapper extends BaseMapper<Dict> {
    /**
     * 根据父节点id查询子节点列表
     * @param parentId
     * @return
     */
    List<Dict> findListByParentId(Long parentId);
    /**
     * 判断是否是父节点
     * @param Id
     * @return
     */
    Integer countIsParent(Long Id);

    /**
     * 根据父节点的code查找所有的子节点
     * @param dictCode
     * @return
     */
    List<Dict> findDictListByDictCode(String dictCode);

}
