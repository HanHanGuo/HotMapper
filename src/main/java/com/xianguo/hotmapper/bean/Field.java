package com.xianguo.hotmapper.bean;

import com.xianguo.hotmapper.enums.SymbolEnmu;

import lombok.Data;

@Data
public class Field {
	private String dataBase;
	private String field;
	private Class<?> classType;
	private SymbolEnmu Symbol;
	
	public Field() {}
	
	public Field(String dataBase,String field,Class<?> classType) {
		this.dataBase = dataBase;
		this.field = field;
		this.classType = classType;
		Symbol = Symbol.EQUAL;//默认等于
	}
}
