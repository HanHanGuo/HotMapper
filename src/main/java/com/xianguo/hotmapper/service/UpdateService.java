package com.xianguo.hotmapper.service;

import java.util.List;

/**
 * 更新的接口
 * @author:鲜果
 * @date:2019年1月21日
 * @param <T>
 */
public interface UpdateService<T> {
	/**
	 * 更新
	 * @author:鲜果
	 * @date:2019年1月16日
	 * @param t 更新条件
	 * @return
	 * Integer 更新数量
	 */
	public Integer update(T t);
	
	/**
	 * 更新多条
	 * @author:鲜果
	 * @date:2019年1月16日
	 * @param t 更新条件
	 * @return
	 * Integer 更新数量
	 */
	public Integer update(List<T> t);
}
