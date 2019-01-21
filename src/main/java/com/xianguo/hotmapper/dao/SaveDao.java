package com.xianguo.hotmapper.dao;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;

import com.xianguo.hotmapper.bean.Table;
import com.xianguo.hotmapper.provider.SaveProvider;

/**
 * 保存的实现
 * @author:鲜果
 * @date:2019年1月21日
 * @param <T>
 */
public interface SaveDao<T> {
	/**
	 * 保存
	 * @author:鲜果
	 * @date:2019年1月16日
	 * @param t 保存实体
	 * @return
	 * Integer 保存数量
	 */
	@InsertProvider(type=SaveProvider.class,method="saveProvider")
	public Integer save(@Param("bean")T t,@Param("table")Table table,@Param("class")Class<? extends T> classes);
}
