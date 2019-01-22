package com.xianguo.hotmapper.provider;

import java.util.Map;

import com.xianguo.hotmapper.bean.FieldValue;
import com.xianguo.hotmapper.sql.Sql;
import com.xianguo.hotmapper.util.FieldFilter;

public class SelectProvider extends Provider {
	
	public String selectProvider(Object obj) {
		loadBean(obj);
		Map<String, FieldValue> fields = FieldFilter.filterEmptyByMap(table.getFieldsIncludeId(), t);
    	String sql = Sql.SQL(
				Sql.SELECT(table.getFieldsIncludeId()), 
				Sql.FROM(table.getName()), 
				Sql.WHERE(fields,"bean"),
				Sql.ORDERBY(table.getOrderByFields()),
				Sql.LIMIT(1)
				);
    	return sql;
	}
	
}
