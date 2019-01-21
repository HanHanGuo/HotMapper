package com.xianguo.hotmapper.provider;

import java.util.Map;

import org.apache.ibatis.binding.MapperMethod.ParamMap;

import com.xianguo.hotmapper.bean.FieldValue;
import com.xianguo.hotmapper.sql.Sql;
import com.xianguo.hotmapper.util.FieldFilter;

public class DeleteProvider extends Provider {
	
	public String deleteProvider(ParamMap<Object> obj) {
		loadBean(obj);
		Map<String, FieldValue> fields = FieldFilter.filterEmptyByMap(table.getFieldsIncludeId(), t);
		String sql = Sql.SQL(
				Sql.DELETE(table.getName()),
				Sql.WHERE(fields, "bean")
				);
		return sql;
	}
	
}
