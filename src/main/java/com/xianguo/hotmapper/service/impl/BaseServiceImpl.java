package com.xianguo.hotmapper.service.impl;

import java.lang.reflect.ParameterizedType;

import com.xianguo.hotmapper.bean.Table;
import com.xianguo.hotmapper.container.Container;

/**
 * 基础service实现类，对hotmapper加载信息到hotmapper容器
 * @author 鲜果
 * @date 2019年1月30日上午10:18:50
 * @param <T>
 */
public abstract class BaseServiceImpl<T,DAO> {
	public Class<T> classes;
	
	public Table table;
	
    @SuppressWarnings("unchecked")
	protected BaseServiceImpl() {
    	classes = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    	table = Container.load(classes);
    }
	
	/**
	 * 获取实体所对应的dao
	 * @author 鲜果
	 * @date 2019年1月30日上午10:15:30
	 * @return
	 * DAO
	 */
	public abstract DAO getDao();
}
