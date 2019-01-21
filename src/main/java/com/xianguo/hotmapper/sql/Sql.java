package com.xianguo.hotmapper.sql;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.xianguo.hotmapper.bean.Field;
import com.xianguo.hotmapper.bean.FieldValue;
import com.xianguo.hotmapper.interfaces.SymbolInterface;

public class Sql {
	
	public static StringBuilder SELECT(Map<String, Field> fields) {
		StringBuilder sb = new StringBuilder();
		for(String key : fields.keySet()) {
			if(!fields.get(key).getIsCondition()) {
				if(StringUtils.isEmpty(sb.toString())) {
					sb.append(fields.get(key).getDataBase());
				}else {
					sb.append(',').append(fields.get(key).getDataBase());
				}
			}
		}
		StringBuilder select = new StringBuilder();
		select.append("select ");
		select.append(sb);
		return select;
	}
	
	public static StringBuilder FROM(String tableName) {
		StringBuilder sb = new StringBuilder();
		sb.append("from ");
		sb.append(tableName);
		return sb;
	}
	
	public static StringBuilder WHERE() {
		return new StringBuilder().append("where");
	}
	
	public static StringBuilder WHERE(FieldValue field,String parName) {
		return WHERE(field,parName,true);
	}
	
	public static StringBuilder WHERE(Field field,String parName) {
		return WHERE(field,parName,true);
	}
	
	public static StringBuilder WHERE(Map<String, FieldValue> fields,String parName) {
		return WHERE(fields,parName,true);
	}
	
	public static StringBuilder WHERE(FieldValue field,String parName,Boolean isBean) {
		StringBuilder sb = new StringBuilder();
		StringBuilder where = new StringBuilder();
		where.append("where ");
		sb.append(field.getField().getDataBase()).append(field.getField().getSymbol().getSymbol(new SymbolInterface() {
			@Override
			public String getPar() {
				StringBuilder ymsb = new StringBuilder();
				if(isBean) {
					ymsb.append("#{").append(parName).append(".").append(field.getField().getField()).append("}");
				}else {
					ymsb.append("#{").append(parName).append("}");
				}
				return ymsb.toString();
			}
		}));
		
		where.append(sb);
		return where;
	}
	
	public static StringBuilder WHERE(Field field,String parName,Boolean isBean) {
		StringBuilder sb = new StringBuilder();
		StringBuilder where = new StringBuilder();
		where.append("where ");
		sb.append(field.getDataBase()).append(field.getSymbol().getSymbol(new SymbolInterface() {
			
			@Override
			public String getPar() {
				StringBuilder ymsb = new StringBuilder();
				if(isBean) {
					ymsb.append("#{").append(parName).append(".").append(field.getField()).append("}");
				}else {
					ymsb.append("#{").append(parName).append("}");
				}
				return ymsb.toString();
			}
		}));
		where.append(sb);
		return where;
	}
	
	public static StringBuilder WHERE(Map<String, FieldValue> fields,String parName,Boolean isBean) {
		if(fields.keySet().size()<=0) {
			return new StringBuilder();
		}
		StringBuilder sb = new StringBuilder();
		StringBuilder where = new StringBuilder();
		where.append("where ");
		for(String key : fields.keySet()) {
			if(!StringUtils.isEmpty(sb.toString())) {
				sb.append(" and");
			}
			sb.append(fields.get(key).getField().getDataBase()).append(fields.get(key).getField().getSymbol().getSymbol(new SymbolInterface() {
				
				@Override
				public String getPar() {
					StringBuilder ymsb = new StringBuilder();
					if(isBean) {
						ymsb.append("#{").append(parName).append(".").append(fields.get(key).getField().getField()).append("}");
					}else {
						ymsb.append("#{").append(parName).append("}");
					}
					return ymsb.toString();
				}
			}));
		}
		where.append(sb);
		return where;
	}
	
	public static StringBuilder SET(Map<String, FieldValue> fields) {
		if(fields.keySet().size()<=0) {
			return new StringBuilder();
		}
		StringBuilder sb = new StringBuilder();
		StringBuilder where = new StringBuilder();
		where.append("set ");
		for(String key : fields.keySet()) {
			if(!fields.get(key).getField().getIsCondition()) {
				if(StringUtils.isEmpty(sb.toString())) {
					sb.append(fields.get(key).getField().getDataBase()).append(" = ").append("#{").append(fields.get(key).getField().getField()).append("}");
				}else {
					sb.append(",").append(fields.get(key).getField().getDataBase()).append(" = ").append("#{").append(fields.get(key).getField().getField()).append("}");
				}
			}
		}
		where.append(sb);
		return where;
	}
	
	public static StringBuilder UPDATE(String tableName) {
		StringBuilder sb = new StringBuilder();
		sb.append("update ");
		sb.append(tableName);
		return sb;
	}
	
	public static StringBuilder DELETE(String tableName) {
		StringBuilder sb = new StringBuilder();
		sb.append("delete from ");
		sb.append(tableName);
		return sb;
	}
	
	public static StringBuilder INSERT(String tableName,Map<String, FieldValue> fields) {
		StringBuilder sb = new StringBuilder();
		StringBuilder head = new StringBuilder();
		head.append("insert into ");
		head.append(tableName);
		head.append("(");
		for(String key : fields.keySet()) {
			if(!fields.get(key).getField().getIsCondition()) {
				if(StringUtils.isEmpty(sb.toString())) {
					sb.append(fields.get(key).getField().getDataBase());
				}else {
					sb.append(", ").append(fields.get(key).getField().getDataBase());
				}
			}
		}
		head.append(sb).append(")");
		return head;
	}
	
	@SuppressWarnings("unused")
	public static StringBuilder VALUES(Map<String, FieldValue> fields) {
		StringBuilder sb = new StringBuilder();
		StringBuilder head = new StringBuilder();
		head.append("values ");
		head.append("(");
		for(String key : fields.keySet()) {
			if(!fields.get(key).getField().getIsCondition()) {
				if(StringUtils.isEmpty(sb.toString())) {
					sb.append("#{").append(fields.get(key).getField().getField()).append("}");
				}else {
					sb.append("#{").append(fields.get(key).getField().getField()).append("}");
				}
			}
		}
		head.append(sb).append(")");
		return head;
	}
	
	public static StringBuilder LIMIT(Integer one,Integer two) {
		StringBuilder sb = new StringBuilder();
		sb.append("LIMIT ");
		sb.append(one);
		sb.append(",");
		sb.append(two);
		return sb;
	}
	
	public static StringBuilder LIMIT(Integer one) {
		StringBuilder sb = new StringBuilder();
		sb.append("LIMIT ");
		sb.append(one);
		return sb;
	}
	
	public static StringBuilder SCRIPT(StringBuilder... sqls) {
		StringBuilder sb = new StringBuilder();
		sb.append("<script>");
		sb.append(SQL(sqls));
		sb.append("</script>");
		return sb;
	}
	
	public static String SQL(StringBuilder... sqls) {
		StringBuilder sb = new StringBuilder();
		for(StringBuilder sql : sqls) {
			sb.append(' ').append(sql);
		}
		return sb.toString();
	}
}
