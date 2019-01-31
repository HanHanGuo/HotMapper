package com.xianguo.hotmapper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.xianguo.hotmapper.enums.OrderByEnmu;

/**
 * 数据库排序注解，查询时更具当前注解排序。
 * @author 鲜果
 * @date 2019年1月31日上午11:23:22
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OrderBy {
	public OrderByEnmu value();
}
