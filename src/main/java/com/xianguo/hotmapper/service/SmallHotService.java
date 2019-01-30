package com.xianguo.hotmapper.service;

/**
 * 轻量hotservice接口，只实现增删改查，不处理关系
 * @author 鲜果
 * @date 2019年1月30日上午10:21:54
 * @param <T>
 */
public interface SmallHotService<T> extends SaveService<T>,DeleteService<T>,UpdateService<T>,SelectService<T> {

}
