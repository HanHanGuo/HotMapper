package com.xianguo.hotmapper.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.xianguo.hotmapper.bean.Field;
import com.xianguo.hotmapper.bean.FieldValue;
import com.xianguo.hotmapper.enums.ClassTypeEnmu;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FieldFilter {
	
	
	
	
	/**
	 * 封装Field
	 * @author:鲜果
	 * @date:2019年1月18日
	 * @param field
	 * @param value
	 * @return
	 * FieldValue
	 */
	public static FieldValue packingField(Field field, Object value) {
		FieldValue fieldValue = new FieldValue();
		fieldValue.setField(field);
		fieldValue.setValue(value);
		return fieldValue;
	}
	
	/**
	 * 封装Bean
	 * @author:鲜果
	 * @date:2019年1月18日
	 * @param field
	 * @param value
	 * @return
	 * FieldValue
	 */
	public static FieldValue packingBean(Field fieldBean, Object value) {
		try {
			FieldValue fieldValue = new FieldValue();
			fieldValue.setField(fieldBean);
			java.lang.reflect.Field field = value.getClass().getDeclaredField(fieldBean.getField());
			Object valBean = field.get(value);
			fieldValue.setValue(valBean);
			return fieldValue;
		} catch (NoSuchFieldException e) {
			log.error(e.getMessage(), e);
			return null;
		} catch (SecurityException e) {
			log.error(e.getMessage(), e);
			return null;
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage(), e);
			return null;
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 过滤NULL值Map
	 * 
	 * @author:鲜果
	 * @date:2019年1月17日
	 * @param fields
	 * @return Map<String,Field> 过滤后的字段
	 */
	public static Map<String, FieldValue> filterNULLByMap(Map<String, Field> fields, Object value) {
		try {
			Map<String, FieldValue> feldValues = new HashMap<>();
			Iterator<Entry<String, Field>> it = fields.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Field> entry = it.next();
				java.lang.reflect.Field field = value.getClass().getDeclaredField(entry.getValue().getField());
				field.setAccessible(true);
				Object obj = field.get(value);
				if (obj == null) {
					continue;
				}
				FieldValue fieldValue = new FieldValue();
				fieldValue.setField(entry.getValue());
				fieldValue.setValue(obj);
				feldValues.put(fieldValue.getField().getField(), fieldValue);
			}
			return feldValues;
		} catch (NoSuchFieldException e) {
			log.error(e.getMessage(), e);
			return null;
		} catch (SecurityException e) {
			log.error(e.getMessage(), e);
			return null;
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage(), e);
			return null;
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 过滤空值Map
	 * 
	 * @author:鲜果
	 * @date:2019年1月17日
	 * @param fields
	 * @return Map<String,Field> 过滤后的字段
	 */
	public static Map<String, FieldValue> filterEmptyByMap(Map<String, Field> fields, Object value) {
		try {
			Map<String, FieldValue> feldValues = new HashMap<>();
			Iterator<Entry<String, Field>> it = fields.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Field> entry = it.next();
				java.lang.reflect.Field field = value.getClass().getDeclaredField(entry.getValue().getField());
				field.setAccessible(true);
				Object obj = field.get(value);
				if (obj == null) {
					continue;
				}
				FieldValue fieldValue = new FieldValue();
				fieldValue.setField(entry.getValue());
				fieldValue.setValue(obj);
				switch (ClassTypeEnmu.getEnmuByClass(field.getType())) {
				case String_CLASS_NAME:
					if (!StringUtils.isEmpty((String) obj)) {
						feldValues.put(fieldValue.getField().getField(), fieldValue);
					}
					break;
				default:
					feldValues.put(fieldValue.getField().getField(), fieldValue);
					break;
				}
			}
			return feldValues;
		} catch (NoSuchFieldException e) {
			log.error(e.getMessage(), e);
			return null;
		} catch (SecurityException e) {
			log.error(e.getMessage(), e);
			return null;
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage(), e);
			return null;
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
}
