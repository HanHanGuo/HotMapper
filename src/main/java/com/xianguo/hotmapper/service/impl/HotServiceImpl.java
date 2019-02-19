package com.xianguo.hotmapper.service.impl;

import java.util.List;

import com.xianguo.hotmapper.dao.HotDao;
import com.xianguo.hotmapper.service.HotService;

import lombok.extern.slf4j.Slf4j;

/**
 * 增删改查以及条件处理实现类
 * 
 * @author 鲜果
 * @date 2019年1月30日上午10:16:04
 * @param <T> 实体类
 * @param <DAO> 对应dao类
 */
public abstract class HotServiceImpl<T, DAO extends HotDao<T>> extends ViewServiceImpl<T, DAO> implements HotService<T> {
	
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
