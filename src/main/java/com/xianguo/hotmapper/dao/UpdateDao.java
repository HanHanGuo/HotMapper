package com.xianguo.hotmapper.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import com.xianguo.hotmapper.bean.Table;

/**
 * 更新的实现
 * @author:鲜果
 * @date:2019年1月21日
 * @param <T>
 */
public interface UpdateDao<T> {
	/**
	 * 更新
	 * @author:鲜果
	 * @date:2019年1月16日
	 * @param t 更新条件
	 * @return
	 * Integer 更新数量
	 */
	@UpdateProvider(type=com.xianguo.hotmapper.provider.UpdateProvider.class,method="updateProvider")
	public Integer update(@Param("bean")T t,@Param("table")Table table,@Param("class")Class<? extends T> classes);
}
