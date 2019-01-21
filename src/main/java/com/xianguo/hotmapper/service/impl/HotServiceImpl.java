package com.xianguo.hotmapper.service.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.xianguo.hotmapper.bean.Table;
import com.xianguo.hotmapper.container.Container;
import com.xianguo.hotmapper.dao.HotDao;
import com.xianguo.hotmapper.service.HotService;
import com.xianguo.hotmapper.util.PreparedStatementUtil;

public abstract class HotServiceImpl<T,DAO extends HotDao<T>> implements HotService<T> {
	
	private Class<T> classes;
	
	private Table table;
	
    @SuppressWarnings("unchecked")
	protected HotServiceImpl() {
    	classes = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    	table = Container.load(classes);
    }

	@Override
	public List<T> selectList(T t) {
		return (List<T>) PreparedStatementUtil.convertBeanByList(classes, getDao().selectList(t, table,classes), table);
	}

	@Override
	public T selectById(String id) {
		return (T) PreparedStatementUtil.convertBeanByMap(classes,getDao().selectById(id, table,classes),table);
	}

	@Override
	public Integer deleteById(String id) {
		return getDao().deleteById(id, table,classes);
	}

	@Transactional
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
	
	@Transactional
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
	
	public abstract DAO getDao();
}
