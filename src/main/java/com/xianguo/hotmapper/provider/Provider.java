package com.xianguo.hotmapper.provider;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.binding.MapperMethod.ParamMap;

import com.xianguo.hotmapper.bean.Table;

public class Provider {


	protected Object t = null;
	protected List<Object> list = null;
	protected String id = null;
	protected Table table = null;
	protected Class<?> classes = null;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void load(Object obj) {
		if(obj instanceof ParamMap) {
			ParamMap<Object> par = (ParamMap<Object>)obj;
			this.table = (Table) par.get("table");
			this.classes = (Class<?>) par.get("class");
		}else if(obj instanceof HashMap){
			HashMap<String,Object> par = (HashMap)obj;
			this.table = (Table) par.get("table");
			this.classes = (Class<?>) par.get("class");
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void loadId(Object obj) {
		load(obj);
		if(obj instanceof ParamMap) {
			ParamMap<Object> par = (ParamMap<Object>)obj;
			this.id = (String)par.get("id");
		}else if(obj instanceof HashMap){
			HashMap<String,Object> par = (HashMap)obj;
			this.id = (String)par.get("id");
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void loadBean(Object obj){
		load(obj);
		if(obj instanceof ParamMap) {
			ParamMap<Object> par = (ParamMap<Object>)obj;
			this.t = par.get("bean");
		}else if(obj instanceof HashMap){
			HashMap<String,Object> par = (HashMap)obj;
			this.t = par.get("bean");
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void loadList(Object obj){
		load(obj);
		if(obj instanceof ParamMap) {
			ParamMap<Object> par = (ParamMap<Object>)obj;
			this.list = (List<Object>) par.get("list");
		}else if(obj instanceof HashMap){
			HashMap<String,Object> par = (HashMap)obj;
			this.list = (List<Object>) par.get("list");
		}
	}
}
