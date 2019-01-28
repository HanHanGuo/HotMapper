package com.xianguo.hotmapper.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.xianguo.hotmapper.bean.Table;
import com.xianguo.hotmapper.provider.SelectByIdProvider;
import com.xianguo.hotmapper.provider.SelectListProvider;

/**
 * 查询的实现
 * @author:鲜果
 * @date:2019年1月21日
 * @param <T>
 */
public interface SelectDao<T> {
	/**
	 * 根据条件查询所有
	 * @author:鲜果
	 * @date:2019年1月16日
	 * @param t 条件
	 * @return
	 * List<T> 返回实体列表
	 */
	@SelectProvider(type=SelectListProvider.class,method="selectListProvider")
	public List<Map<String, Object>> selectList(@Param("bean")T t,@Param("table")Table table,@Param("class")Class<? extends T> classes);
	
	/**
	 * 根据条件查询一条
	 * @author:鲜果
	 * @date:2019年1月16日
	 * @param id id
	 * @return
	 * T 返回单个实体
	 */
	@SelectProvider(type=com.xianguo.hotmapper.provider.SelectProvider.class,method="selectProvider")
	public Map<String, Object> select(@Param("bean")T t,@Param("table")Table table,@Param("class")Class<? extends T> classes);
	
	/**
	 * 根据id查询一条
	 * @author:鲜果
	 * @date:2019年1月16日
	 * @param id id
	 * @return
	 * T 返回单个实体
	 */
	@SelectProvider(type=SelectByIdProvider.class,method="selectByIdProvider")
	public Map<String, Object> selectById(@Param("id")String id,@Param("table")Table table,@Param("class")Class<? extends T> classes);
}
