package com.xianguo.hotmapper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.xianguo.hotmapper.enums.FieldIsNull;
import com.xianguo.hotmapper.enums.FieldType;

/**
 * 逆向工程-字段信息
 * @author 武昱坤
 * @date 2019年3月14日
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldInfo {
	FieldType type() default FieldType.VARCHAR;
	String length();
	FieldIsNull isNull() default FieldIsNull.NULL;
	String detail() default "";
}
