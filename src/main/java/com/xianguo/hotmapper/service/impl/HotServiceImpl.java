package com.xianguo.hotmapper.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.Page;
import com.xianguo.hotmapper.bean.Table;
import com.xianguo.hotmapper.container.Container;
import com.xianguo.hotmapper.dao.HotDao;
import com.xianguo.hotmapper.service.HotService;
import com.xianguo.hotmapper.util.PreparedStatementUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class HotServiceImpl<T,DAO extends HotDao<T>> implements HotService<T> {
	
	public Class<T> classes;
	
	public Table table;

	@Autowired
	private BeanFactory beanFactory;
	
    @SuppressWarnings("unchecked")
	protected HotServiceImpl() {
    	classes = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    	table = Container.load(classes);
    }
    
    @Override
	public T select(T t) {
    	return PreparedStatementUtil.convertBeanByMap(classes,getDao().select(t, table, classes),table);
    }

    @Override
	public T select(T t,Boolean openRelation) {
    	if(openRelation) {
    		return Relation(PreparedStatementUtil.convertBeanByMap(classes,getDao().select(t, table, classes),table), 1) ;
    	}else {
    		return PreparedStatementUtil.convertBeanByMap(classes,getDao().select(t, table, classes),table);
    	}
    }

    @Override
	public T select(T t,Boolean openRelation,int hierarchy) {
    	if(openRelation) {
    		return Relation(PreparedStatementUtil.convertBeanByMap(classes,getDao().select(t, table, classes),table), hierarchy) ;
    	}else {
    		return PreparedStatementUtil.convertBeanByMap(classes,getDao().select(t, table, classes),table);
    	}
    }
    
    
	@Override
	public List<T> selectList(T t) {
		return selectList(t,false,0);
	}
	
	@Override
	public List<T> selectList(T t,Boolean openRelation) {
		if(openRelation) {
			return selectList(t,true,1);
		}else {
			return selectList(t);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> selectList(T t,Boolean openRelation,int hierarchy) {
		Object obj = getDao().selectList(t, table,classes);
		if(obj instanceof Page<?>) {
			Page<T> page = (Page<T>)obj;
			List<T> beans = PreparedStatementUtil.convertBeanByList(classes, (List<Map<String,Object>>)obj, table);
			page.clear();
			for(T bean : beans) {
				page.add(bean);
			}
			if(openRelation) {
				return Relation(page,hierarchy);
			}else {
				return page;
			}
		}else {
			if(openRelation) {
				return Relation(PreparedStatementUtil.convertBeanByList(classes, (List<Map<String,Object>>)obj, table),hierarchy);
			}else {
				return PreparedStatementUtil.convertBeanByList(classes, (List<Map<String,Object>>)obj, table);
			}
		}
	}

	@Override
	public T selectById(String id) {
		return PreparedStatementUtil.convertBeanByMap(classes,getDao().selectById(id, table,classes),table);
	}
	
	@Override
	public T selectById(String id,Boolean openRelation) {
		if(openRelation) {
			return Relation(PreparedStatementUtil.convertBeanByMap(classes,getDao().selectById(id, table,classes),table),1);
		}else {
			return selectById(id);
		}
	}
	
	@Override
	public T selectById(String id,Boolean openRelation,int hierarchy) {
		if(openRelation) {
			return Relation(PreparedStatementUtil.convertBeanByMap(classes,getDao().selectById(id, table,classes),table),hierarchy);
		}else {
			return selectById(id);
		}
	}

	@Override
	public Integer deleteById(String id) {
		return getDao().deleteById(id, table,classes);
	}

	@Override
	public Integer deleteByIds(List<String> ids) {
		Integer sum = 0;
		for(String id : ids) {
			sum += getDao().deleteById(id, table, classes);
		}
		return sum;
	}

	@Override
	public Integer delete(T t) {
		return getDao().delete(t, table,classes);
	}

	@Override
	public Integer save(T t) {
		return getDao().save(t, table,classes);
	}
	
	@Override
	public Integer save(List<T> t) {
		Integer sum = 0;
		for(T bean : t) {
			sum += getDao().save(bean, table, classes);
		}
		return sum;
	}

	@Override
	public Integer update(T t) {
		return getDao().update(t, table,classes);
	}

	@Override
	public Integer update(List<T> t) {
		Integer sum = 0;
		for(T bean : t) {
			sum += getDao().update(bean, table, classes);
		}
		return sum;
	}
	
	public T Relation(T t,Integer hierarchy) {
		if(t == null) {
			return t;
		}
		List<T> list = new ArrayList<>();
		list.add(t);
		list = Relation(list,hierarchy);
		if(list.size() > 0) {
			return list.get(0);
		}else {
			return t;
		}
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> Relation(List<T> t,int hierarchy) {
		try {
			if(hierarchy <= 0) {
				return t;
			}
			hierarchy--;
			for(T value : t) {
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
	
	public abstract DAO getDao();
	
}
