package com.xianguo.hotmapper.provider;

import java.util.List;

import org.apache.ibatis.binding.MapperMethod.ParamMap;

import com.xianguo.hotmapper.bean.Table;

public class Provider {

	protected Object t = null;
	protected List<Object> list = null;
	protected String id = null;
	protected Table table = null;
	protected Class<?> classes = null;
	
	public void load(ParamMap<Object> obj) {
		this.table = (Table) obj.get("table");
		this.classes = (Class<?>) obj.get("class");
	}
	
	public void loadId(ParamMap<Object> obj) {
		load(obj);
		this.id = (String)obj.get("id");
	}
	
	public void loadBean(ParamMap<Object> obj){
		load(obj);
		this.t = obj.get("bean");
	}
	
	@SuppressWarnings("unchecked")
	public void loadList(ParamMap<Object> obj){
		load(obj);
		this.list = (List<Object>) obj.get("list");
	}
}
