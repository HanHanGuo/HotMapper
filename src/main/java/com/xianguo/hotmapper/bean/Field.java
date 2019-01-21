package com.xianguo.hotmapper.bean;

import lombok.Data;

@Data
public class Field {
	private String dataBase;
	private String field;
	private Class<?> classType;
	
	public Field() {}
	
	public Field(String dataBase,String field,Class<?> classType) {
		this.dataBase = dataBase;
		this.field = field;
		this.classType = classType;
	}
}
