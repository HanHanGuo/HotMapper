package com.xianguo.hotmapper.bean;

import com.xianguo.hotmapper.enums.FieldIsNull;
import com.xianguo.hotmapper.enums.FieldType;
import com.xianguo.hotmapper.enums.OrderByEnmu;
import com.xianguo.hotmapper.enums.SymbolEnmu;

import lombok.Data;

/**
 * 字段映射实体 
 * @author 武昱坤
 * @date 2019年3月14日
 *
 */
@Data
public class Field {
	private String dataBase;//数据库字段名称
	private String field;//实体字段名称
	private Class<?> classType;//实体字段类型
	private SymbolEnmu Symbol;//查询符号
	private Boolean isCondition;//是否是条件字段
	private OrderByEnmu orderByEnmu;//排序
	
	private FieldType filedType; //字段类型
	private FieldIsNull filedIsNull;//字段是否可以为空
	private String length;//字段长度
	private String detail;//字段注释（详细信息描述）
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
