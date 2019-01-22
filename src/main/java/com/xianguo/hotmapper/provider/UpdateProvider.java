package com.xianguo.hotmapper.provider;

import java.util.Map;

import com.xianguo.hotmapper.bean.FieldValue;
import com.xianguo.hotmapper.sql.Sql;
import com.xianguo.hotmapper.util.FieldFilter;

public class UpdateProvider extends Provider {
	public String updateProvider(Object obj) {
		loadBean(obj);
		Map<String, FieldValue> fieldsValue = FieldFilter.filterNULLByMap(table.getFields(), t);
		String sql = Sql.SQL(
				Sql.UPDATE(table.getName()),
				Sql.SET(fieldsValue),
				Sql.WHERE(table.getId(),"bean")
				);
		return sql;
	}
}
