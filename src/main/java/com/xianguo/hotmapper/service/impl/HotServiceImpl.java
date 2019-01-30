package com.xianguo.hotmapper.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xianguo.hotmapper.dao.HotDao;
import com.xianguo.hotmapper.service.HotService;
import com.xianguo.hotmapper.util.PreparedStatementUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 增删改查以及条件处理实现类
 * @author 鲜果
 * @date 2019年1月30日上午10:16:04
 * @param <T>
 * @param <DAO>
 */
@Slf4j
public abstract class HotServiceImpl<T,DAO extends HotDao<T>> extends SmallHotServiceImpl<T,DAO> implements HotService<T> {

	@Autowired
	private BeanFactory beanFactory;

    @Override
	public T select(T t,Boolean openRelation) {
    	if(openRelation) {
    		return SelectRelation(PreparedStatementUtil.convertBeanByMap(classes,getDao().select(t, table, classes),table), 1) ;
    	}else {
    		return PreparedStatementUtil.convertBeanByMap(classes,getDao().select(t, table, classes),table);
    	}
    }

    @Override
	public T select(T t,Boolean openRelation,int hierarchy) {
    	if(openRelation) {
    		return SelectRelation(PreparedStatementUtil.convertBeanByMap(classes,getDao().select(t, table, classes),table), hierarchy) ;
    	}else {
    		return PreparedStatementUtil.convertBeanByMap(classes,getDao().select(t, table, classes),table);
    	}
    }
    
	@Override
	public List<T> selectList(T t,Boolean openRelation) {
		if(openRelation) {
			List<T> list = selectList(t);
			return SelectRelation(list,1);
		}else {
			return selectList(t);
		}
	}
	
	@Override
	public List<T> selectList(T t,Boolean openRelation,int hierarchy) {
		if(openRelation) {
			List<T> list = selectList(t);
			return SelectRelation(list,hierarchy);
		}else {
			return selectList(t);
		}
	}
	
	@Override
	public T selectById(String id,Boolean openRelation) {
		if(openRelation) {
			return SelectRelation(PreparedStatementUtil.convertBeanByMap(classes,getDao().selectById(id, table,classes),table),1);
		}else {
			return selectById(id);
		}
	}
	
	@Override
	public T selectById(String id,Boolean openRelation,int hierarchy) {
		if(openRelation) {
			return SelectRelation(PreparedStatementUtil.convertBeanByMap(classes,getDao().selectById(id, table,classes),table),hierarchy);
		}else {
			return selectById(id);
		}
	}
	
	/**
	 * 查询关系处理
	 * @author 鲜果
	 * @date 2019年1月30日上午10:12:07
	 * @param t 需要处理的实体
	 * @param hierarchy 需要传播的层数
	 * @return
	 * T
	 */
	public T SelectRelation(T t,Integer hierarchy) {
		if(t == null) {
			return t;
		}
		List<T> list = new ArrayList<>();
		list.add(t);
		list = SelectRelation(list,hierarchy);
		if(list.size() > 0) {
			return list.get(0);
		}else {
			return t;
		}
	}
	
	/**
	 * 查询关系处理
	 * @author 鲜果
	 * @date 2019年1月30日上午10:12:33
	 * @param t 要处理关系的集合
	 * @param hierarchy 要传播的层数
	 * @return
	 * List<T>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> SelectRelation(List<T> t,int hierarchy) {
		try {
			if(hierarchy <= 0) {
				return t;
			}
			hierarchy--;
			for(T value : t) {
				Class<?> classes = value.getClass();
				for(Field field : classes.getDeclaredFields()) {
					com.xianguo.hotmapper.annotation.Relation relation = null;
					if((relation = field.getAnnotation(com.xianguo.hotmapper.annotation.Relation.class))!=null) {
						field.setAccessible(true);
						HotServiceImpl telationService = (HotServiceImpl) beanFactory.getBean(relation.service());
						Class<?> parClass = telationService.classes;
						Object par = parClass.newInstance();
						Field parField = parClass.getDeclaredField(relation.pk());
						parField.setAccessible(true);
						if(parField != null) {
							Field valField = classes.getDeclaredField(relation.fk());
							valField.setAccessible(true);
							if(valField != null) {
								Object objBean = valField.get(value);
								if(objBean != null && objBean instanceof String) {//校验是否为空字符串
									if(StringUtils.isEmpty((String)objBean)) {
										continue;
									}
								}
								if(objBean != null) {
									parField.set(par, objBean);
	
									if(field.getType().equals(List.class)) {
										List<Object> valueBean = telationService.selectList(par,true,hierarchy);
										if(valueBean != null && valueBean.size()>0) {
											field.set(value, valueBean);
										}else {
											field.set(value, null);
										}
									} else {
										Object valueBean = telationService.select(par,true,hierarchy);
										field.set(value, valueBean);
									}
								}
							}
						}
					}
				}
			}
			return t;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return t;
		}
	}
}
