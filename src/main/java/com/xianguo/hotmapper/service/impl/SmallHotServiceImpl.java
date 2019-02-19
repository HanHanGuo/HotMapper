package com.xianguo.hotmapper.service.impl;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.xianguo.hotmapper.dao.HotDao;
import com.xianguo.hotmapper.service.SmallHotService;
import com.xianguo.hotmapper.util.PreparedStatementUtil;

public abstract class SmallHotServiceImpl<T,DAO extends HotDao<T>> extends BaseServiceImpl<T,DAO> implements SmallHotService<T> {
	
    @Override
	public T select(T t) {
    	return PreparedStatementUtil.convertBeanByMap(classes,getDao().select(t, table, classes),table);
    }
	
	@Override
	public T selectById(String id) {
		return PreparedStatementUtil.convertBeanByMap(classes,getDao().selectById(id, table,classes),table);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> selectList(T t) {
		Object obj = getDao().selectList(t, table,classes);
		if(obj instanceof Page<?>) { //处理PageHelper兼容性
			Page<T> page = (Page<T>)obj;
			List<T> beans = PreparedStatementUtil.convertBeanByList(classes, (List<Map<String,Object>>)obj, table);
			page.clear();
			for(T bean : beans) {
				page.add(bean);
			}
			return page;
		}else {
			return PreparedStatementUtil.convertBeanByList(classes, (List<Map<String,Object>>)obj, table);
		}
	}
	
	@Override
	public Integer deleteById(String id) {
		return getDao().deleteById(id, table,classes);
	}

	@Override
	public Integer deleteByIds(List<String> ids) {
		Integer sum = 0;
		for(String id : ids) {
			sum += getDao().deleteById(id, table, classes);
		}
		return sum;
	}

	@Override
	public Integer delete(T t) {
		return getDao().delete(t, table,classes);
	}

	@Override
	public Integer save(T t) {
		return getDao().save(t, table,classes);
	}
	
	@Override
	public Integer save(List<T> t) {
		Integer sum = 0;
		for(T bean : t) {
			sum += getDao().save(bean, table, classes);
		}
		return sum;
	}

	@Override
	public Integer update(T t) {
		return getDao().update(t, table,classes);
	}

	@Override
	public Integer update(List<T> t) {
		Integer sum = 0;
		for(T bean : t) {
			sum += getDao().update(bean, table, classes);
		}
		return sum;
	}
}
