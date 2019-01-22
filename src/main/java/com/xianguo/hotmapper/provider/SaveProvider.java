package com.xianguo.hotmapper.provider;

import java.util.Map;

import com.xianguo.hotmapper.bean.FieldValue;
import com.xianguo.hotmapper.sql.Sql;
import com.xianguo.hotmapper.util.FieldFilter;

public class SaveProvider extends Provider {
	
	public String saveProvider(Object obj) {
		loadBean(obj);
		Map<String, FieldValue> fieldsValue = FieldFilter.filterEmptyByMap(table.getFieldsIncludeId(), t);
		
		String sql = Sql.SQL(
				Sql.INSERT(table.getName(), fieldsValue),
				Sql.VALUES(fieldsValue)
				);
		return sql;
	}
}
