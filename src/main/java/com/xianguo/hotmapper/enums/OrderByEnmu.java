package com.xianguo.hotmapper.enums;

/**
 * 排序枚举
 * @author:鲜果
 * @date:2019年1月22日
 */
public enum OrderByEnmu {
	/**
	 * 正序
	 */
	ASC("asc"),
	/**
	 * 倒叙
	 */
	DESC("desc");
	
	private final String keyword;
	
	public String keyword() {
		return this.keyword;
	}
 
	OrderByEnmu(String keyword) {
		this.keyword = keyword;
	}
}
