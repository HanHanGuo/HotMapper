package com.xianguo.hotmapper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.xianguo.hotmapper.enums.SymbolEnmu;

/**
 * 查询条件符号注解
 * @author 鲜果
 * @date 2019年1月31日上午11:25:15
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Symbol {
	public SymbolEnmu value();
}
