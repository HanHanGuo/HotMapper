package com.xianguo.hotmapper.dao;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Param;

import com.xianguo.hotmapper.bean.Table;
import com.xianguo.hotmapper.provider.DeleteByIdProvider;

/**
 * 删除的实现
 * @author:鲜果
 * @date:2019年1月21日
 * @param <T>
 */
public interface DeleteDao<T> {
	
	/**
	 * 根据id删除一条
	 * @author:鲜果
	 * @date:2019年1月16日
	 * @param id id
	 * @return
	 * Integer 删除数量
	 */
	@DeleteProvider(type=DeleteByIdProvider.class,method="deleteByIdProvider")
	public Integer deleteById(@Param("id")String id,@Param("table")Table table,@Param("class")Class<? extends T> classes);
	
	/**
	 * 根据条件删除多条
	 * @author:鲜果
	 * @date:2019年1月16日
	 * @param t 条件
	 * @return
	 * Integer 删除数量
	 */
	@DeleteProvider(type=com.xianguo.hotmapper.provider.DeleteProvider.class,method="deleteProvider")
	public Integer delete(@Param("bean")T t,@Param("table")Table table,@Param("class")Class<? extends T> classes);
	
}
