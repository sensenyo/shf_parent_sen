package base;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import util.CastUtil;

import java.util.List;
import java.util.Map;

@Transactional(propagation = Propagation.REQUIRED)
public abstract class BaseServiceImpl<T> {

    public abstract BaseMapper<T> getMapper();

    public int insert(T t) {
        return getMapper().insert(t);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public T getById(Long id) {
        return getMapper().getById(id);
    }

    public int update(T role) {
        return getMapper().update(role);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<T> findPage(Map filters) {
        int pageSize = CastUtil.castInt(filters.get("pageSize"), 10);
        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
        PageHelper.startPage(pageNum, pageSize);
        Page<T> page = getMapper().findPage(filters);
        return new PageInfo<>(page, 10);
    }

    public int delete(Long id) {
        return getMapper().delete(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<T> getList(){
        return getMapper().getList();
    }
}
