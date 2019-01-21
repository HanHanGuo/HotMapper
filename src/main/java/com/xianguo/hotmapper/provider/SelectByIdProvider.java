package com.xianguo.hotmapper.provider;

import org.apache.ibatis.binding.MapperMethod.ParamMap;

import com.xianguo.hotmapper.sql.Sql;

public class SelectByIdProvider extends Provider {
	
	public String selectByIdProvider(ParamMap<Object> obj) {
		loadId(obj);
		String sql = Sql.SQL(
				Sql.SELECT(table.getFieldsIncludeId()), 
				Sql.FROM(table.getName()),
				Sql.WHERE(table.getId(),"id",false),
				Sql.LIMIT(1));
		return sql;
	}
}
