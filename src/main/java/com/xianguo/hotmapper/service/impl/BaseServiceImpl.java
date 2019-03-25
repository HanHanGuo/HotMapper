package com.xianguo.hotmapper.service.impl;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.xianguo.hotmapper.bean.Table;
import com.xianguo.hotmapper.container.Container;
import com.xianguo.hotmapper.util.PreparedStatementUtil;

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
     * 处理表和实体的转换
     * @author 鲜果
     * @date 2019年2月19日
     * @param maps
     * @return
     * List<T>
     */
    @SuppressWarnings("unchecked")
	public List<T> MapToBean(List<Map<String, Object>> maps){
    	if(maps instanceof Page<?>) {//处理PageHelper兼容性
    		Page<T> page = (Page<T>)maps;
    		List<T> beans = PreparedStatementUtil.convertBeanByList(classes, maps, table);
    		page.clear();
    		for(T t : beans) {
    			page.add(t);
    		}
    		return page;
    	}else {
    		return PreparedStatementUtil.convertBeanByList(classes, maps, table);
    	}
    }
    
    /**
     * 处理表和实体的转换
     * @author 鲜果
     * @date 2019年2月19日
     * @param maps
     * @return
     * List<T>
     */
    public T MapToBean(Map<String, Object> map) {
    	return PreparedStatementUtil.convertBeanByMap(classes, map, table);
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
