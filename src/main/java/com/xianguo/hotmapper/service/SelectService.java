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
	 * 根据条件查询一条并处理一层关系
	 * @author:鲜果
	 * @date:2019年1月21日
	 * @param t
	 * @param openRelation
	 * @return
	 * T
	 */
	public T select(T t,Boolean openRelation);
	
	/**
	 * 根据条件查询一条并处理自定义层关系
	 * @author:鲜果
	 * @date:2019年1月21日
	 * @param t
	 * @param openRelation
	 * @param hierarchy
	 * @return
	 * T
	 */
	public T select(T t,Boolean openRelation,int hierarchy);
	
	/**
	 * 根据条件查询所有
	 * @author:鲜果
	 * @date:2019年1月16日
	 * @param t 条件
	 * @return
	 * List<T> 返回实体列表
	 */
	public List<T> selectList(T t);
	
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
	 * 按条件查询list并处理一层关系返回实体
	 * @author:鲜果
	 * @date:2019年1月21日
	 * @param t 条件
	 * @param openRelation 是否开启关系处理
	 * @return
	 * List<T>
	 */
	public List<T> selectList(T t,Boolean openRelation);
	
	/**
	 * 按条件查询list并处理自定义层关系返回实体
	 * @author:鲜果
	 * @date:2019年1月21日
	 * @param t 条件
	 * @param openRelation 是否开启关系处理
	 * @param hierarchy 处理关系层级
	 * @return
	 * List<T>
	 */
	public List<T> selectList(T t,Boolean openRelation,int hierarchy);
	
	/**
	 * 更具id查询实体并处理1层关系
	 * @author:鲜果
	 * @date:2019年1月21日
	 * @param id
	 * @param openRelation
	 * @return
	 * T
	 */
	public T selectById(String id,Boolean openRelation);
	
	/**
	 * 更具id查询实体并处理指定层级关系
	 * @author:鲜果
	 * @date:2019年1月21日
	 * @param id
	 * @param openRelation
	 * @param hierarchy
	 * @return
	 * T
	 */
	public T selectById(String id,Boolean openRelation,int hierarchy);
}
