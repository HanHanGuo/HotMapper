package com.xianguo.hotmapper.bean;

import com.xianguo.hotmapper.enums.OrderByEnmu;
import com.xianguo.hotmapper.enums.SymbolEnmu;

import lombok.Data;

@Data
public class Field {
	private String dataBase;
	private String field;
	private Class<?> classType;
	private SymbolEnmu Symbol;
	private Boolean isCondition;
	private OrderByEnmu orderByEnmu;//排序
	
	public Field() {
		isCondition = false;//默认是字段
		Symbol = SymbolEnmu.EQUAL;//默认等于
	}
	
	public Field(String dataBase,String field,Class<?> classType) {
		this.dataBase = dataBase;
		this.field = field;
		this.classType = classType;
		isCondition = false;//默认是字段
		Symbol = SymbolEnmu.EQUAL;//默认等于
	}
}
