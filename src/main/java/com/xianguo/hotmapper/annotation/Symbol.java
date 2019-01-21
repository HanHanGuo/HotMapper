package com.xianguo.hotmapper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.xianguo.hotmapper.enums.SymbolEnmu;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Symbol {
	public SymbolEnmu value();
}
