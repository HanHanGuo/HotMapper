package com.xianguo.hotmapper.container;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

import com.xianguo.hotmapper.annotation.AnalysisType;
import com.xianguo.hotmapper.annotation.Condition;
import com.xianguo.hotmapper.annotation.HotTransient;
import com.xianguo.hotmapper.annotation.Id;
import com.xianguo.hotmapper.annotation.NoCache;
import com.xianguo.hotmapper.annotation.OrderBy;
import com.xianguo.hotmapper.annotation.Relation;
import com.xianguo.hotmapper.annotation.Symbol;
import com.xianguo.hotmapper.bean.Table;
import com.xianguo.hotmapper.enums.AnalysusTypeEnmu;
import com.xianguo.hotmapper.util.FieldNameUtil;

/**
 * hotmapper容器
 * 
 * @author 鲜果
 * @date 2019年1月30日上午10:19:36
 */
public class Container {

	/**
	 * 容器
	 */
	public static Map<String, Table> tables = new HashMap<String, Table>();

	/**
	 * 加载下类信息，以及注解信息
	 * 
	 * @author 鲜果
	 * @date 2019年1月30日上午10:19:52
	 * @param classes
	 * @return Table
	 */
	public static Table load(Class<?> classes) {
		synchronized (tables) {
			com.xianguo.hotmapper.annotation.Table tableName = classes.getAnnotation(com.xianguo.hotmapper.annotation.Table.class);
			if (tableName == null) {
				throw new RuntimeException(classes.getName() + "未加Table注解");
			}
			if (StringUtils.isEmpty(tableName.value())) {
				throw new RuntimeException(classes.getName() + "Table注解值为空");
			}
			Table table;
			if ((table = tables.get(tableName.value())) != null) {// 如果容器已存在，则不继续解析
				return table;
			}
			table = new Table();
			table.setName(tableName.value());
			AnalysusTypeEnmu nameStyle = null;
			AnalysisType analysisType = classes.getAnnotation(com.xianguo.hotmapper.annotation.AnalysisType.class);
			if (analysisType == null) {
				nameStyle = AnalysusTypeEnmu.NONE;
			} else {
				nameStyle = analysisType.value();
			}

			Field[] fields = classes.getDeclaredFields();
			com.xianguo.hotmapper.bean.Field idByAnn = null;
			com.xianguo.hotmapper.bean.Field idByName = null;
			Map<String, com.xianguo.hotmapper.bean.Field> beanField = new HashMap<>();//表字段包括id
			Map<String, com.xianguo.hotmapper.bean.Relation> relations = new HashMap<>();// 关系字段map
			Map<String, com.xianguo.hotmapper.bean.Field> beanFieldAll = new HashMap<>();//实体字段包括被忽略的字段
			List<com.xianguo.hotmapper.bean.Field> orderBys = new ArrayList<>();// 排序字段
			for (Field field : fields) {
				if (field.getAnnotation(HotTransient.class) == null && field.getAnnotation(Relation.class) == null) {// 处理数据库字段
					com.xianguo.hotmapper.annotation.Field databaseFieldNameAnn = field.getAnnotation(com.xianguo.hotmapper.annotation.Field.class);
					if (idByAnn == null) {
						Id id = field.getAnnotation(Id.class);
						if (id != null) {
							idByAnn = new com.xianguo.hotmapper.bean.Field(databaseFieldNameAnn != null ? databaseFieldNameAnn.value() : !StringUtils.isEmpty(id.value()) ? id.value() : FieldNameUtil.nameToDataBaseName(nameStyle, field.getName()), field.getName(), field.getType());
						}
						if ("id".equals(field.getName().toLowerCase())) {
							idByName = new com.xianguo.hotmapper.bean.Field(databaseFieldNameAnn == null ? FieldNameUtil.nameToDataBaseName(nameStyle, field.getName()) : databaseFieldNameAnn.value(), field.getName(), field.getType());
						}
					}
					com.xianguo.hotmapper.bean.Field BeanFieldSave = new com.xianguo.hotmapper.bean.Field(databaseFieldNameAnn == null ? FieldNameUtil.nameToDataBaseName(nameStyle, field.getName()) : databaseFieldNameAnn.value(), field.getName(), field.getType());
					Symbol symbol = null;
					if ((symbol = field.getAnnotation(Symbol.class)) != null) {
						BeanFieldSave.setSymbol(symbol.value());
					}
					if (field.getAnnotation(Condition.class) != null) {
						BeanFieldSave.setIsCondition(true);
					}
					OrderBy orderBy = null;
					if ((orderBy = field.getAnnotation(OrderBy.class)) != null) {
						BeanFieldSave.setOrderByEnmu(orderBy.value());
						orderBys.add(BeanFieldSave);
					}
					beanField.put(field.getName(), BeanFieldSave);
					beanFieldAll.put(field.getName(), BeanFieldSave);//存放所有字段map
				}
				if(field.getAnnotation(HotTransient.class) != null && field.getType() == String.class){//被忽略的字段并且为String才处理
					com.xianguo.hotmapper.bean.Field BeanFieldSave = new com.xianguo.hotmapper.bean.Field();//
					BeanFieldSave.setClassType(field.getType());
					BeanFieldSave.setDataBase(field.getName());
					beanFieldAll.put(field.getName(), BeanFieldSave);//存放所有字段map
				}
				Relation relation = null;
				if ((relation = field.getAnnotation(Relation.class)) != null) {// 处理关系字段
					com.xianguo.hotmapper.bean.Relation relationBean = new com.xianguo.hotmapper.bean.Relation();
					relationBean.setService(relation.service());
					relationBean.setPk(relation.pk());
					relationBean.setFk(relation.fk());
					relationBean.setFieldName(field.getName());
					relationBean.setClassType(field.getType());
					if (field.getAnnotation(NoCache.class) != null) {
						relationBean.setCache(false);// 不缓存
					}
					relations.put(field.getName(), relationBean);
				}
			}
			table.setFieldsIncludeId(beanField);
			table.setFieldsIncludeAll(beanFieldAll);
			if (idByAnn != null) {
				table.setId(idByAnn);
			} else if (idByName != null) {
				table.setId(idByName);
			} else {
				throw new RuntimeException(classes.getName() + "无法确定ID");
			}
			Map<String, com.xianguo.hotmapper.bean.Field> mapCopy = beanField.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));//表字段不包括ID
			mapCopy.remove(table.getId().getField());
			table.setFields(mapCopy);
			table.setOrderByFields(orderBys);// 放入需要排序的字段
			table.setRelationFields(relations);// 存入关系字段
			tables.put(table.getName(), table);
			return table;
		}
	}

}
