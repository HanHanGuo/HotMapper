package com.xianguo.hotmapper.enums;

/**
 * 逆向工程-数据库字段类型枚举
 * @author 武昱坤
 * @date 2019年3月14日
 *
 */
public enum FieldType {
	VARCHAR("varchar"),TEXT("text");
	
	private final String value;
	
	private FieldType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
