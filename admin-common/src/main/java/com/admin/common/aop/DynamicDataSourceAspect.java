package com.admin.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.admin.common.annotation.TargetDataSource;
import com.admin.common.ds.DynamicDataSource;

/**
 * AOP动态切换数据源
 * @author xiamh
 *
 */
@Aspect
@Component
public class DynamicDataSourceAspect {
	
	Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

	@Before("@annotation(ds)")
	public void changeDataSource(JoinPoint point,TargetDataSource ds) {
//		logger.info("     ##### 注解设置数据源:{}",ds.value().getDs());
		DynamicDataSource.setDataSourceType(ds.value().getDs());
	}
	
	@After("@annotation(ds)")
	public void restoreDataSource(JoinPoint point,TargetDataSource ds) {
//		logger.info("     ##### 注解清除数据源:{}",ds.value().getDs());
		DynamicDataSource.clearDataSourceType();
	}
	
}
