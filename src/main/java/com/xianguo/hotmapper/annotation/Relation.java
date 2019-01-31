package com.xianguo.hotmapper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.xianguo.hotmapper.service.impl.HotServiceImpl;

/**
 * 关联关系主注解，加上此注解的实体在查询的时候，会自动更具关系注入值。
 * @author 鲜果
 * @date 2019年1月31日上午11:23:49
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Relation {
	/**
	 * 外键表service实现类
	 * @author 鲜果
	 * @date 2019年1月31日上午11:24:33
	 * @return
	 * Class<? extends HotServiceImpl>
	 */
	@SuppressWarnings("rawtypes")
	public Class<? extends HotServiceImpl> service();
	/**
	 * 当前表外键
	 * @author 鲜果
	 * @date 2019年1月31日上午11:24:52
	 * @return
	 * String
	 */
	public String fk();
	/**
	 * 被关联表主键
	 * @author 鲜果
	 * @date 2019年1月31日上午11:25:02
	 * @return
	 * String
	 */
	public String pk();
}
