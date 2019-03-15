package com.xianguo.hotmapper.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.xianguo.hotmapper.bean.Table;
import com.xianguo.hotmapper.container.Container;
import com.xianguo.hotmapper.util.SqlSessionFactoryUtil;

@Component
public class ReverseRunner implements ApplicationRunner{
	
	@Autowired
	private SqlSessionFactoryUtil sqlSessionFactoryUtil;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		Map<String, Table> tables = Container.tables;
		for(String key : tables.keySet()) {
			Table table = tables.get(key);
			Container.reverseTable(sqlSessionFactoryUtil, table.getFieldClasses(), table);
		}
	}

}
