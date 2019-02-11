package com.xianguo.hotmapper.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xianguo.hotmapper.bean.TempBean;
import com.xianguo.hotmapper.dao.HotDao;
import com.xianguo.hotmapper.service.HotService;
import com.xianguo.hotmapper.util.PreparedStatementUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 增删改查以及条件处理实现类
 * 
 * @author 鲜果
 * @date 2019年1月30日上午10:16:04
 * @param <T> 实体类
 * @param <DAO> 对应dao类
 */
@Slf4j
public abstract class HotServiceImpl<T, DAO extends HotDao<T>> extends SmallHotServiceImpl<T, DAO> implements HotService<T> {

	@Autowired
	private BeanFactory beanFactory;

	@Override
	public T select(T t, Boolean openRelation) {
		if (openRelation) {
			return SelectRelation(PreparedStatementUtil.convertBeanByMap(classes, getDao().select(t, table, classes), table), 1, new HashMap<>());
		} else {
			return PreparedStatementUtil.convertBeanByMap(classes, getDao().select(t, table, classes), table);
		}
	}

	@Override
	public T select(T t, Integer hierarchy) {
		if (hierarchy > 0) {
			return SelectRelation(PreparedStatementUtil.convertBeanByMap(classes, getDao().select(t, table, classes), table), hierarchy, new HashMap<>());
		} else {
			return PreparedStatementUtil.convertBeanByMap(classes, getDao().select(t, table, classes), table);
		}
	}

	/**
	 * 关系缓存处理查询（外部无需调用所以是private，内部传播使用）
	 * 
	 * @author 鲜果
	 * @date 2019年2月11日上午9:33:01
	 * @param t         条件
	 * @param hierarchy 关系深度
	 * @param temp      单次查询数据缓存
	 * @return T 查询结果
	 */
	private T select(T t, Integer hierarchy, Map<String, List<TempBean>> temp) {
		if (hierarchy > 0) {
			if (temp == null) {
				temp = new HashMap<>();
			}
			List<TempBean> list = temp.get(classes.getName());
			if (list == null) {
				list = new ArrayList<>();
				temp.put(classes.getName(), list);
			}
			TempBean tempBean = new TempBean();
			Map<String, Object> value = getDao().select(t, table, classes);
			tempBean.setValue(value);
			list.add(tempBean);
			return SelectRelation(PreparedStatementUtil.convertBeanByMap(classes, value, table), hierarchy, temp);
		} else {
			return select(t);
		}
	}

	@Override
	public List<T> selectList(T t, Boolean openRelation) {
		if (openRelation) {
			List<T> list = selectList(t);
			return SelectRelation(list, 1, new HashMap<>());
		} else {
			return selectList(t);
		}
	}

	@Override
	public List<T> selectList(T t, Integer hierarchy) {
		if (hierarchy > 0) {
			List<T> list = selectList(t);
			return SelectRelation(list, hierarchy, new HashMap<>());
		} else {
			return selectList(t);
		}
	}

	/**
	 * 关系缓存处理查询（外部无需调用所以是private，内部传播使用）
	 * 
	 * @author 鲜果
	 * @date 2019年2月11日上午11:05:56
	 * @param t         条件
	 * @param hierarchy 关系传播深度
	 * @param temp      缓存
	 * @return List<T> 返回值
	 */
	private List<T> selectList(T t, Integer hierarchy, Map<String, List<TempBean>> temp) {
		if (hierarchy > 0) {
			if (temp == null) {
				temp = new HashMap<>();
			}
			List<TempBean> list = temp.get(classes.getName());
			if (list == null) {
				list = new ArrayList<>();
				temp.put(classes.getName(), list);
			}
			List<Map<String, Object>> values = getDao().selectList(t, table, classes);
			for (Map<String, Object> value : values) {
				TempBean tempBean = new TempBean();
				tempBean.setValue(value);
				list.add(tempBean);
			}
			return SelectRelation(PreparedStatementUtil.convertBeanByList(classes, values, table), hierarchy, temp);
		} else {
			return selectList(t);
		}
	}

	@Override
	public T selectById(String id, Boolean openRelation) {
		if (openRelation) {
			return SelectRelation(PreparedStatementUtil.convertBeanByMap(classes, getDao().selectById(id, table, classes), table), 1, new HashMap<>());
		} else {
			return selectById(id);
		}
	}

	@Override
	public T selectById(String id, Integer hierarchy) {
		if (hierarchy > 0) {
			return SelectRelation(PreparedStatementUtil.convertBeanByMap(classes, getDao().selectById(id, table, classes), table), hierarchy, new HashMap<>());
		} else {
			return selectById(id);
		}
	}

	/**
	 * 查询关系处理
	 * 
	 * @author 鲜果
	 * @date 2019年1月30日上午10:12:07
	 * @param t         需要处理的实体
	 * @param hierarchy 需要传播的层数
	 * @return T
	 */
	public T SelectRelation(T t, Integer hierarchy, Map<String, List<TempBean>> temp) {
		if (t == null) {
			return t;
		}
		List<T> list = new ArrayList<>();
		list.add(t);
		list = SelectRelation(list, hierarchy, temp);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return t;
		}
	}

	/**
	 * 查询关系处理
	 * 
	 * @author 鲜果
	 * @date 2019年1月30日上午10:12:33
	 * @param t         要处理关系的集合
	 * @param hierarchy 要传播的层数
	 * @return List<T>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> SelectRelation(List<T> t, Integer hierarchy, Map<String, List<TempBean>> temp) {
		try {
			if (hierarchy <= 0) {
				temp.clear();//清理缓存
				System.gc();//回收垃圾
				return t;
			}
			hierarchy--;
			for (T value : t) {
				Class<?> classes = value.getClass();
				for (Field field : classes.getDeclaredFields()) {
					com.xianguo.hotmapper.annotation.Relation relation = null;
					if ((relation = field.getAnnotation(com.xianguo.hotmapper.annotation.Relation.class)) != null) {
						field.setAccessible(true);
						HotServiceImpl telationService = (HotServiceImpl) beanFactory.getBean(relation.service());
						Class<?> parClass = telationService.classes;
						Field parField = parClass.getDeclaredField(relation.pk());
						parField.setAccessible(true);
						if (parField != null) {
							Field valField = classes.getDeclaredField(relation.fk());
							valField.setAccessible(true);
							if (valField != null) {
								Object objBean = valField.get(value);
								if (objBean != null && objBean instanceof String) {// 校验是否为空字符串
									if (StringUtils.isEmpty((String) objBean)) {
										continue;
									}
								}
								if (objBean != null) {
									Object par = parClass.newInstance();
									parField.set(par, objBean);
									if (field.getType().equals(List.class)) {
										List<Object> valueBean = telationService.selectList(par, hierarchy, temp);
										if (valueBean != null && valueBean.size() > 0) {
											field.set(value, valueBean);
										} else {
											field.set(value, null);
										}
									} else {
										for(TempBean tempBean : temp.get(telationService.classes.getName())) {
											String dataBaseName = telationService.table.getFields().get(relation.fk()).getDataBase();//转换fk实体外键为数据库对应字段名
											Object tempValue = tempBean.getValue().get(dataBaseName);
											if(tempValue == null) {
												continue;
											}
											if(tempValue.equals(field.get(value))) {//判断关系是否成立
												field.set(value, field.get(value));
												return t;//从缓存中拿到值后直接返回，不查询数据库
											}
										}
										Object valueBean = telationService.select(par, hierarchy, temp);
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
			log.error(e.getMessage(), e);
			return t;
		}
	}
}
