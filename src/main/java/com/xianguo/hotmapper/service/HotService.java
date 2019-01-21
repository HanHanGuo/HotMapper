package com.xianguo.hotmapper.service;

/**
 * 增删该查全部的接口
 * @author:鲜果
 * @date:2019年1月21日
 * @param <T>
 */
public interface HotService<T> extends SaveService<T>,DeleteService<T>,UpdateService<T>,SelectService<T> {
	
	
	
}
