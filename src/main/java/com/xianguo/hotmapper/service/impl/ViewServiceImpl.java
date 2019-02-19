package com.xianguo.hotmapper.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.Page;
import com.xianguo.hotmapper.bean.Relation;
import com.xianguo.hotmapper.bean.TempBean;
import com.xianguo.hotmapper.dao.HotDao;
import com.xianguo.hotmapper.service.ViewService;
import com.xianguo.hotmapper.util.MapUtil;
import com.xianguo.hotmapper.util.PreparedStatementUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ViewServiceImpl<T,DAO extends HotDao<T>> extends BaseServiceImpl<T,DAO> implements ViewService<T> {

	@Autowired
	private BeanFactory beanFactory;
	
	@Override
	public T select(T t) {
    	return PreparedStatementUtil.convertBeanByMap(classes,getDao().select(t, table, classes),table);
    }
	
	@Override
	public T selectById(String id) {
		return PreparedStatementUtil.convertBeanByMap(classes,getDao().selectById(id, table,classes),table);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> selectList(T t) {
		Object obj = getDao().selectList(t, table,classes);
		if(obj instanceof Page<?>) { //处理PageHelper兼容性
			Page<T> page = (Page<T>)obj;
			List<T> beans = PreparedStatementUtil.convertBeanByList(classes, (List<Map<String,Object>>)obj, table);
			page.clear();
			for(T bean : beans) {
				page.add(bean);
			}
			return page;
		}else {
			return PreparedStatementUtil.convertBeanByList(classes, (List<Map<String,Object>>)obj, table);
		}
	}
	
	@Override
	public T select(T t, Boolean openRelation) {
		if (openRelation) {
			Map<String, List<TempBean>> temp = new HashMap<>();// 创建缓存
			return select(t, 1, temp);
		} else {
			return select(t);
		}
	}

	@Override
	public T select(T t, Integer hierarchy) {
		if (hierarchy > 0) {
			Map<String, List<TempBean>> temp = new HashMap<>();// 创建缓存
			return select(t, hierarchy, temp);
		} else {
			return select(t);
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
			Map<String, Object> value = getDao().select(t, table, classes);
			if (value == null) {
				return null;
			}
			T bean = PreparedStatementUtil.convertBeanByMap(classes, value, table);
			TempBean tempBean = new TempBean();// 记录缓存
			tempBean.setValue(value);
			tempBean.setBean(bean);
			list.add(tempBean);
			return SelectRelation(bean, hierarchy, temp);
		} else {
			return select(t);
		}
	}

	@Override
	public List<T> selectList(T t, Boolean openRelation) {
		if (openRelation) {
			Map<String, List<TempBean>> temp = new HashMap<>();// 创建缓存
			return selectList(t, 1, temp);
		} else {
			return selectList(t);
		}
	}

	@Override
	public List<T> selectList(T t, Integer hierarchy) {
		if (hierarchy > 0) {
			Map<String, List<TempBean>> temp = new HashMap<>();// 创建缓存
			return selectList(t, hierarchy, temp);
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
			List<T> beans = PreparedStatementUtil.convertBeanByList(classes, values, table);
			Integer index = 0;
			for (Map<String, Object> value : values) {
				TempBean tempBean = new TempBean();
				tempBean.setValue(value);
				tempBean.setBean(beans.get(index));
				list.add(tempBean);// 放入集合，避免重复转换。
				index++;
			}
			if(beans.size() > 0) {
				TempBean tempBean = new TempBean();
				tempBean.setValue(MapUtil.object2Map(t));
				tempBean.setBean(beans);
			}
			return SelectRelation(beans, hierarchy, temp);
		} else {
			return selectList(t);
		}
	}

	@Override
	public T selectById(String id, Boolean openRelation) {
		if (openRelation) {
			Map<String, List<TempBean>> temp = new HashMap<>();// 创建缓存
			return selectById(id, 1, temp);
		} else {
			return selectById(id);
		}
	}

	@Override
	public T selectById(String id, Integer hierarchy) {
		if (hierarchy > 0) {
			Map<String, List<TempBean>> temp = new HashMap<>();// 创建缓存
			return selectById(id, hierarchy, temp);
		} else {
			return selectById(id);
		}
	}
	
	/**
	 * 关系缓存处理查询（外部无需调用所以是private，内部传播使用）
	 * @author 鲜果
	 * @date 2019年2月11日下午3:33:57
	 * @param id id
	 * @param hierarchy 关系传播深度
	 * @param temp 缓存
	 * @return
	 * T
	 */
	private T selectById(String id, Integer hierarchy, Map<String, List<TempBean>> temp) {
		if (hierarchy > 0) {
			if (temp == null) {
				temp = new HashMap<>();
			}
			List<TempBean> list = temp.get(classes.getName());
			if (list == null) {
				list = new ArrayList<>();
				temp.put(classes.getName(), list);
			}
			Map<String, Object> value = getDao().selectById(id, table, classes);
			if (value == null) {
				return null;
			}
			T bean = PreparedStatementUtil.convertBeanByMap(classes, value, table);
			TempBean tempBean = new TempBean();// 记录缓存
			tempBean.setValue(value);
			tempBean.setBean(bean);
			list.add(tempBean);
			return SelectRelation(bean, hierarchy, temp);
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
				temp.clear();// 清理缓存
				System.gc();// 回收垃圾
				return t;
			}
			hierarchy--;
			Map<String, Relation> relations = table.getRelationFields();
			for (T value : t) {
				for (String key : relations.keySet()) {
					Relation relation = relations.get(key);
					Field field = classes.getDeclaredField(relation.getFieldName());//关联实体字段
					Field valueField = classes.getDeclaredField(relation.getFk());//关联外键字段
					if(valueField == null) {
						throw new RuntimeException(classes.getName()+"类无"+relation.getFk()+"字段，请注意关系配置。");
					}
					field.setAccessible(true);
					ViewServiceImpl telationService = (ViewServiceImpl) beanFactory.getBean(relation.getService());
					Class<?> parClass = telationService.classes;
					Field parField = parClass.getDeclaredField(relation.getPk());
					parField.setAccessible(true);
					if (parField != null) {
						Field valField = classes.getDeclaredField(relation.getFk());
						valField.setAccessible(true);
						if (valField != null) {
							Object objBean = valField.get(value);
							if (objBean != null && objBean instanceof String) {// 校验是否为空字符串
								if (StringUtils.isEmpty((String) objBean)) {
									continue;
								}
							}
							

							List<TempBean> tempBens = temp.get(telationService.classes.getName());
							Boolean isTemp = true;
							if (tempBens != null && relation.getCache()) {//有缓存和允许读取缓存时，才进行缓存读取。
								for (TempBean tempBean : tempBens) {
									String dataBaseName = telationService.table.getFieldsIncludeId().get(relation.getPk()).getDataBase();// 转换fk实体外键为数据库对应字段名
									Object tempValue = tempBean.getValue().get(dataBaseName);
									if (tempValue == null) {
										continue;
									}
									if (tempValue.equals(valField.get(value)) && field.getType().equals(tempBean.getBean().getClass()) ) {// 判断关系和类型是否成立
										field.set(value, tempBean.getBean());//从缓存取出实体
										isTemp = false;
										break;// 从缓存中拿到值后直接进入下次循环，不查询数据库
									}
								}
							}
							
							if (objBean != null && isTemp) {
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
									Object valueBean = telationService.select(par, hierarchy, temp);
									field.set(value, valueBean);
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
