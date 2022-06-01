package base;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface BaseService<T> {
    //插入角色
    int insert(T t);

    //根据id获取角色
    T getById(Long id);

    //修改根据id角色
    int update(T role);

    //逻辑删除角色
    int delete(Long id);

    //查询所有角色
    List<T> getList();

    //分页查询
    PageInfo<T> findPage(Map filters);
}
