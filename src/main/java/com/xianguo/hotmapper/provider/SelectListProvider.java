package com.xianguo.hotmapper.provider;

import java.util.Map;

import com.xianguo.hotmapper.bean.FieldValue;
import com.xianguo.hotmapper.sql.Sql;
import com.xianguo.hotmapper.util.FieldFilter;

/**
 * 查询处理接口
 * @author:鲜果
 * @date:2019年1月21日
 */
public class SelectListProvider extends Provider{
	
    public String selectListProvider(Object obj) {
    	loadBean(obj);//加载参数
    	Map<String, FieldValue> fields = FieldFilter.filterEmptyByMap(table.getFieldsIncludeId(), t);
    	String sql = Sql.SQL(
				Sql.SELECT(table.getFieldsIncludeId()), 
				Sql.FROM(table.getName()), 
				Sql.WHERE(fields,"bean"),
				Sql.ORDERBY(table.getOrderByFields())
				);
    	return sql;
    }

}
