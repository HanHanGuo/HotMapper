package com.xianguo.hotmapper.annotation;

public @interface HotTransient {
	Class<?> value() default RuntimeException.class; 
}
