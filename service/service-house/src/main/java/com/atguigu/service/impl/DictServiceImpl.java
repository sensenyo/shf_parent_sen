package com.atguigu.service.impl;

import base.BaseMapper;
import base.BaseServiceImpl;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.atguigu.entity.Dict;
import com.atguigu.mapper.DictMapper;
import com.atguigu.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@Service
@Service(interfaceClass = DictService.class)
@Transactional(propagation = Propagation.REQUIRED)
public class DictServiceImpl extends BaseServiceImpl<Dict> implements DictService {
    @Autowired
    private DictMapper dictMapper;
    @Autowired
    private JedisPool jedisPool;

    @Override
    public BaseMapper<Dict> getMapper() {
        return dictMapper;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Map<String, Object>> findZnodes(Long id) {
        List<Dict> dictList = dictMapper.findListByParentId(id);

        List<Map<String, Object>> mapList = dictList.stream().map(dict -> {
            Map<String, Object> map = new HashMap();
            map.put("id", dict.getId());
            map.put("name", dict.getName());
            map.put("isParent", dictMapper.countIsParent(dict.getId()) > 0);
            return map;
        }).collect(Collectors.toList());

        return mapList;
    }

    @Override
    public List<Dict> findDictListByDictCode(String dictCode) {
        Jedis resource = null;
        try {
            resource = jedisPool.getResource();
            String value = resource.get("dict:dictListByDictCode:" + dictCode);
            if (!StringUtils.isEmpty(value)) {
                return JSON.parseArray(value, Dict.class);
            }
            List<Dict> dictListByDictCode = dictMapper.findDictListByDictCode(dictCode);
            resource.set("dict:dictListByDictCode:" + dictCode, JSON.toJSONString(dictListByDictCode));
            return dictListByDictCode;
        } finally {
            if (resource != null) {
                resource.close();
                if (resource.isConnected()) {
                    resource.disconnect();
                }
            }
        }
    }

    @Override
    public List<Dict> findListByParentId(Long parentId) {
        Jedis resource = null;
        try {
            resource = jedisPool.getResource();
            String value = resource.get("dict:findListByParentId:" + parentId);
            if (!StringUtils.isEmpty(value)) {
                return JSON.parseArray(value, Dict.class);
            }
            List<Dict> listByParentId = dictMapper.findListByParentId(parentId);
            resource.set("dict:findListByParentId:" + parentId, JSON.toJSONString(listByParentId));
            return listByParentId;
        } finally {
            if (resource != null) {
                resource.close();
                if (resource.isConnected()) {
                    resource.disconnect();
                }
            }
        }
    }
}
