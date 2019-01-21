package com.xianguo.hotmapper.service;

import java.util.List;

/**
 * 保存的接口
 * @author:鲜果
 * @date:2019年1月21日
 * @param <T>
 */
public interface SaveService<T> {


	/**
	 * 保存
	 * @author:鲜果
	 * @date:2019年1月16日
	 * @param t 保存实体
	 * @return
	 * Integer 保存数量
	 */
	public Integer save(T t);
	
	/**
	 * 保存实体列表
	 * @author:鲜果
	 * @date:2019年1月16日
	 * @param t
	 * @return
	 * Integer
	 */
	public Integer save(List<T> t);
}
