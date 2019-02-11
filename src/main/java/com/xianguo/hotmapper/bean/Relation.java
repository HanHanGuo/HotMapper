package com.xianguo.hotmapper.bean;

import lombok.Data;

/**
 * 关系实体
 * @author 鲜果
 * @date 2019年2月11日下午2:13:09
 */
@Data
public class Relation {
	/**
	 * service地址
	 */
	private Class<?> service;
	/**
	 * 主键
	 */
	private String pk;
	/**
	 * 外键
	 */
	private String fk;
	/**
	 * 实体字段名称
	 */
	private String fieldName;
	/**
	 * 实体类型
	 */
	private Class<?> classType;
}
