package com.xianguo.hotmapper.enums;

import com.xianguo.hotmapper.interfaces.SymbolInterface;

/**
 * 数据库查询操作符
 * @author 鲜果
 * @date 2019年1月31日上午11:29:23
 */
public enum SymbolEnmu {
	/**
	 * 等于
	 */
	EQUAL(" = "),
	/**
	 * 模糊
	 */
	LIKE(" like "),
	/**
	 * 大于
	 */
	GREATER(" > "),
	/**
	 * 小于
	 */
	LESS(" < "),
	/**
	 * 大于等于
	 */
	GREATER_EQUAL(" >= "),
	/**
	 * 小于等于
	 */
	LESS_EQUAL(" <= ");
	
	private final String symbol;
	
	public String getSymbol(SymbolInterface par) {
		StringBuilder sb = new StringBuilder();
		sb.append(symbol);
		if(symbol.equals(" like ")) {
			sb.append("concat").append("(\"%\",").append(par.getPar()).append(",\"%\")");
		}else {
			sb.append(par.getPar());
		}
		return sb.toString();
	}
 
	SymbolEnmu(String symbol) {
		this.symbol = symbol;
	}
}
