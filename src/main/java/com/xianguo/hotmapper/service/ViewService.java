package com.xianguo.hotmapper.service;

/**
 * 视图专用service只提供查询和关系查询
 * @author 鲜果
 * @date 2019年2月19日
 * @param <T>
 */
public interface ViewService<T> extends SelectService<T>,SelectRelationService<T> {

}
