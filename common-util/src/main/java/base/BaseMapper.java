package base;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface BaseMapper<T> {
    List<T> getList();

    //插入角色
    int insert(T t);

    //根据id获取角色
    T getById(Long id);

    //修改根据id角色
    int update(T T);

    //逻辑删除角色
    int delete(Long id);

    //分页查询
    Page<T> findPage(Map filters);
}
