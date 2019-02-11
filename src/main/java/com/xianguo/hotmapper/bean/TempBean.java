package com.xianguo.hotmapper.bean;

import java.util.Map;

import lombok.Data;

/**
 * 缓存实体
 * @author 鲜果
 * @date 2019年2月11日上午10:51:43
 */
@Data
public class TempBean {
	/**
	 * 值
	 */
	private Map<String, Object> value;
	/**
	 * 实体
	 */
	private Object bean;
}
