package com.xianguo.hotmapper.bean;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class Table{
	/**
	 * 表名
	 */
	private String name;
	/**
	 * id
	 */
	private Field id;
	/**
	 * 字段
	 */
	private Map<String, Field> fields;
	/**
	 * 字段包括id
	 */
	private Map<String, Field> fieldsIncludeId;
	
	/**
	 * 排序字段
	 */
	private List<Field> orderByFields;
}