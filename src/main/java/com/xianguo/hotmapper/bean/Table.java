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
	 * 表字段
	 */
	private Map<String, Field> fields;
	/**
	 * 表字段包括id
	 */
	private Map<String, Field> fieldsIncludeId;
	/**
	 * 实体字段包括被忽略的字段
	 */
	private Map<String, Field> fieldsIncludeAll;
	/**
	 * 关系字段
	 */
	private Map<String, Relation> relationFields;
	/**
	 * 排序字段
	 */
	private List<Field> orderByFields;
	/**
	 * 需要逆向的字段集合
	 */
	private List<Field> reverseFields;
}