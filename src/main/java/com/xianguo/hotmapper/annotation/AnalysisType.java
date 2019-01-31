package com.xianguo.hotmapper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.xianguo.hotmapper.enums.AnalysusTypeEnmu;

/**
 * 表字段命名风格
 * @author 鲜果
 * @date 2019年1月31日上午11:19:47
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AnalysisType {
	AnalysusTypeEnmu value() default AnalysusTypeEnmu.NONE;//解析字段风格默认无风格
}
