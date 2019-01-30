package com.xianguo.hotmapper.service;

import java.util.List;

/**
 * 查询的接口
 * @author:鲜果
 * @date:2019年1月21日
 * @param <T>
 */
public interface SelectService<T> {
	/**
	 * 根据条件查询一条
	 * @author:鲜果
	 * @date:2019年1月21日
	 * @param t
	 * @return
	 * T
	 */
	public T select(T t);
	
	/**
	 * 根据id查询一条
	 * @author:鲜果
	 * @date:2019年1月16日
	 * @param id id
	 * @return
	 * T 返回单个实体
	 */
	public T selectById(String id);
	
	/**
	 * 根据条件查询所有
	 * @author:鲜果
	 * @date:2019年1月16日
	 * @param t 条件
	 * @return
	 * List<T> 返回实体列表
	 */
	public List<T> selectList(T t);
}
