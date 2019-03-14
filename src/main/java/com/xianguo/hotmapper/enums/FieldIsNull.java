package com.xianguo.hotmapper.enums;

/**
 * 逆向工程-字段是否可以为空
 * @author 武昱坤
 * @date 2019年3月14日
 *
 */
public enum FieldIsNull {
	NULL("NULL"), NOT_NULL("NOT NULL");

	private final String value;
	
	private FieldIsNull(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
