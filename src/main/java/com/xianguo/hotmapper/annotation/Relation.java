package com.xianguo.hotmapper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.xianguo.hotmapper.service.impl.HotServiceImpl;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Relation {
	@SuppressWarnings("rawtypes")
	public Class<? extends HotServiceImpl> service();
	public String fk();
	public String pk();
}
