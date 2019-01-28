package com.xianguo.hotmapper.provider;

import com.xianguo.hotmapper.bean.FieldValue;
import com.xianguo.hotmapper.sql.Sql;
import com.xianguo.hotmapper.util.FieldFilter;

public class DeleteByIdProvider extends Provider {
	
	public String deleteByIdProvider(Object obj) {
		loadId(obj);
		FieldValue fieldValue = FieldFilter.packingField(table.getId(), t);
		String sql = Sql.SQL(
				Sql.DELETE(table.getName()), 
				Sql.WHERE(fieldValue,"id",false)
				);
		return sql;
	}
}