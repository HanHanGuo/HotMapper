package com.xianguo.hotmapper.service;

import java.util.List;

/**
 * 删除的接口
 * @author:鲜果
 * @date:2019年1月21日
 * @param <T>
 */
public interface DeleteService<T> {
	
	/**
	 * 根据id删除一条
	 * @author:鲜果
	 * @date:2019年1月16日
	 * @param id id
	 * @return
	 * Integer 删除数量
	 */
	public Integer deleteById(String id);
	
	/**
	 * 根据id列表删除多条
	 * @author:鲜果
	 * @date:2019年1月16日
	 * @param ids id列表
	 * @return
	 * Integer 删除数量
	 */
	public Integer deleteByIds(List<String> ids);
	
	/**
	 * 根据条件删除多条
	 * @author:鲜果
	 * @date:2019年1月16日
	 * @param t 条件
	 * @return
	 * Integer 删除数量
	 */
	public Integer delete(T t);
}
