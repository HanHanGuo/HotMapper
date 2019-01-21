package com.xianguo.hotmapper.util;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xianguo.hotmapper.bean.FieldValue;
import com.xianguo.hotmapper.bean.Table;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PreparedStatementUtil {
	
	/**
	 * 填充参数
	 * @author:鲜果
	 * @date:2019年1月17日
	 * @param values
	 * @param ps
	 * @param fieldValues
	 * void
	 */
	public static void setValue(Map<String, FieldValue> values,PreparedStatement ps,FieldValue... fieldValues) {
		try {
			StringBuilder sb = new StringBuilder();
			StringBuilder head = new StringBuilder();
			head.append("==> Parameters: ");
			int i = 1;
			for(String key : values.keySet()) {
				if(i == 1) {
					sb.append(values.get(key).getValue()).append("(").append(values.get(key).getValue().getClass().getSimpleName()).append(")");
				}else {
					sb.append(", ").append(values.get(key).getValue()).append("(").append(values.get(key).getValue().getClass().getSimpleName()).append(")");
				}
				ps.setObject(i, values.get(key).getValue());
				i++;
			}
			for(FieldValue value : fieldValues) {
				if(i == 1) {
					sb.append(value.getValue()).append("(").append(value.getValue().getClass().getSimpleName()).append(")");
				}else {
					sb.append(", ").append(value.getValue()).append("(").append(value.getValue().getClass().getSimpleName()).append(")");
				}
				ps.setObject(i, value.getValue());
				i++;
			}
			head.append(sb);
			log.info(head.toString());
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
			throw new RuntimeException(e);
		}
	}
	
	
	public static void setValue(FieldValue values,PreparedStatement ps) {
		try {
			StringBuilder sb = new StringBuilder();
			StringBuilder head = new StringBuilder();
			sb.append(values.getValue()).append("(").append(values.getValue().getClass().getSimpleName()).append(")");
			ps.setObject(1, values.getValue());
			head.append(sb);
			log.info(head.toString());
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 转换结果集
	 * @author:鲜果
	 * @date:2019年1月17日
	 * @param rs
	 * @return
	 * List<Map<String,String>>
	 */
	public static List<Map<String, Object>> convertList(ResultSet rs){
		try {
			List<Map<String, Object>> list = new ArrayList<>();
			ResultSetMetaData md = rs.getMetaData();//获取键名
			int columnCount = md.getColumnCount();//获取行的数量
			int row = 0;
			while (rs.next()) {
				row++;
				Map<String,Object> rowData = new HashMap<>();//声明Map
				for (int i = 1; i <= columnCount; i++) {
					rowData.put(md.getColumnName(i), rs.getObject(i));//获取键名及值
				}
				list.add(rowData);
			}
			log.info("<==      Total: "+row);
			return list;
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 转换Map为实体
	 * @author:鲜果
	 * @date:2019年1月17日
	 * @param classes
	 * @param values
	 * @param table
	 * @return
	 * T
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convertBeanByMap(Class<? extends T> classes,Map<String, Object> values,Table table) {
		try {
			if(values == null) {
				return null;
			}
			Object obj = classes.newInstance();
			Boolean isNull = true;
			for(Field field : classes.getDeclaredFields()) {
				field.setAccessible(true);
				com.xianguo.hotmapper.bean.Field fieldBean = table.getFieldsIncludeId().get(field.getName());
				if(fieldBean != null) {
					Object val = values.get(fieldBean.getDataBase());
					field.set(obj, val);
					isNull = false;
				}
			}
			if(isNull) {
				return null;
			}else {
				return (T)obj;
			}
		} catch (InstantiationException e) {
			log.error(e.getMessage(),e);
			return null;
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(),e);
			return null;
		}
	}
	
	/**
	 * 转换list为实体
	 * @author:鲜果
	 * @date:2019年1月17日
	 * @param classes
	 * @param values
	 * @param table
	 * @return
	 * List<T>
	 */
	public static <T> List<T> convertBeanByList(Class<? extends T> classes,List<Map<String, Object>> values,Table table){
		List<T> list = new ArrayList<>();
		for(Map<String, Object> map : values) {
			list.add(convertBeanByMap(classes,map,table));
		}
		return list;
	}
}
