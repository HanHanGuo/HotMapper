package com.xianguo.hotmapper.util;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SqlSessionFactoryUtil {
	
	@Autowired
	private SqlSessionFactoryBean sqlSessionFactoryBean;
	
	//private static SqlSession sqlSession;
	
	private static ThreadLocal<SqlSession> tl = new ThreadLocal<SqlSession>();
	
	public SqlSession getsqlSession() {
		try {
			if(tl.get() == null) {
				tl.set(sqlSessionFactoryBean.getObject().openSession());
				log.info("sqlSession被创建");
			}
			return tl.get();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new RuntimeException("连接数据库失败请检查数据库配置");
		}
	}
	
	
	/*public SqlSession getsqlSession() {
		try {
			if(sqlSession == null) {
				sqlSession = sqlSessionFactoryBean.getObject().openSession();
				log.info("sqlSession被创建");
			}
			return sqlSession;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new RuntimeException("连接数据库失败请检查数据库配置");
		}
	}*/
}
