package com.xianguo.hotmapper.dao;

/**
 * 增删改查的所有实现
 * @author:鲜果
 * @date:2019年1月21日
 * @param <T>
 */
public interface HotDao<T> extends SaveDao<T>,DeleteDao<T>,UpdateDao<T>,SelectDao<T> {
	
}
