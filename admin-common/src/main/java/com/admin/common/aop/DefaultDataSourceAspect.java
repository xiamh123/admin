package com.admin.common.aop;

import java.lang.reflect.Method;

import com.admin.common.enums.DataSourceEnum;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.admin.common.annotation.TargetDataSource;
import com.admin.common.ds.DynamicDataSource;

/**
 * AOP动态切换数据源，当未设置注解时根据包名设置默认数据源
 * 
 * @author xiamh
 *
 */
@Aspect
@Component
public class DefaultDataSourceAspect {

	Logger logger = LoggerFactory.getLogger(DefaultDataSourceAspect.class);

	private static final ThreadLocal<String> threadLocal = new ThreadLocal<String>();

	/**
	 * 设置后台切面，通过注解的方式在接口上不生效，所以自己通过设置切面解析注解
	 */
	@Pointcut("execution(* com.kpmdp..*.mapper..*.*(..))")
	public void pointcut() {
	}
	
	/**
	 * 设置中台切面
	 */
	@Pointcut("execution(* com.kayak.kpms..*.mapper..*.*(..))")
	public void pointcutMidd() {
	}
	
	@Pointcut("execution(* com.baomidou.mybatisplus.mapper..*.*(..))")
	public void pointcutPlus() {
	}

	/**
	 * 中台切面设置数据源
	 * 
	 * @param point
	 * @throws Exception
	 */
	@Before(value = "pointcutMidd()")
	public void changeDataSourceMidd(JoinPoint point) throws Exception {
//		logger.info("********************** 中台 Mapper接口设置数据源 start**********************");

		// 判断当前是否已经设置数据源，设置则不再重复操作
		String ds = DynamicDataSource.getDataSourceType();
		if (StringUtils.isNotBlank(ds)) {
//			logger.info("     ##### 中台 已经设置数据源，不再重复设置");
			return;
		}

		String dataSource = ""; // DataSourceContextHolder.DEFAULT_DS;

		// 得到访问的方法对象
		Method method = this.getMethod(point);

		// 存在@TargetDataSource注解，则优先已注解设置的数据源为准
		if (method.isAnnotationPresent(TargetDataSource.class)) {
			TargetDataSource annotation = method.getAnnotation(TargetDataSource.class);
			// 取出注解中的数据源名
			dataSource = annotation.value().getDs();
		}

		// 当dataSource不为空的时候设置数据源
		if (StringUtils.isNotBlank(dataSource)) {
//			logger.info("     ##### 中台设置数据源:{}", dataSource);
			// 切换数据源
			DynamicDataSource.setDataSourceType(dataSource);
			threadLocal.set("true");
		}

//		logger.info("********************** 中台 Mapper接口设置数据源 end**********************");
	}

	/**
	 * 后台设置数据源
	 * 
	 * @param point
	 * @throws Exception
	 */
	@Before(value = "pointcut()")
	public void changeDataSource(JoinPoint point) throws Exception {
//		logger.info("********************** 后台 Mapper接口设置数据源 start**********************");

		// 判断当前是否已经设置数据源，设置则不再重复操作
		String ds = DynamicDataSource.getDataSourceType();
		if (StringUtils.isNotBlank(ds)) {
//			logger.info("     ##### 后台 已经设置数据源，不再重复设置");
			return;
		}

		String dataSource = ""; // DataSourceContextHolder.DEFAULT_DS;

		// 得到访问的方法对象
		Method method = this.getMethod(point);

		// 获取包名，需要根据包名获取默认的datasource
		String packageName = this.getPackageName(point);

		// 存在@TargetDataSource注解，则优先已注解设置的数据源为准
		if (method.isAnnotationPresent(TargetDataSource.class)) {
			TargetDataSource annotation = method.getAnnotation(TargetDataSource.class);
			// 取出注解中的数据源名
			dataSource = annotation.value().getDs();

		// 若没有@TargetDataSource注解，且当前未设置数据源，则根据包名获取默认数据源
		} else {
			String tmp[] = packageName.split("\\.");
			DataSourceEnum[] dataSourceArr = DataSourceEnum.values();
			for (String t : tmp) {
				for (DataSourceEnum dsTmp : dataSourceArr) {
					if (StringUtils.equals(t, dsTmp.getDs())) {
						dataSource = dsTmp.getDs();
					}
				}
			}
		}

		// 当dataSource不为空的时候设置数据源
		if (StringUtils.isNotBlank(dataSource)) {
//			logger.info("     ##### 后台设置数据源:{}", dataSource);
			// 切换数据源
			DynamicDataSource.setDataSourceType(dataSource);
			threadLocal.set("true");
		}

//		logger.info("********************** 后台 Mapper接口设置数据源 end**********************");
	}

	/**
	 * 清除数据源
	 * 
	 * @param point
	 */
	@After(value = "pointcut() || pointcutMidd()")
	public void restoreDataSource(JoinPoint point) {
		if ("true".equals(threadLocal.get())) {
//			logger.info("     ##### 清除数据源:{}", DynamicDataSource.getDataSourceType());
			DynamicDataSource.clearDataSourceType();
			threadLocal.set("false");
		}

	}
	
	
	@Before(value = "pointcutPlus()")
	public void changeAaa(JoinPoint point) throws Exception {
		
	}
	

	/**
	 * 获取拦截的方法对象
	 * 
	 * @param point
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings("rawtypes")
	public Method getMethod(JoinPoint point) throws NoSuchMethodException, SecurityException {

		// 获得当前访问的class，这里获取到的是代理类，需要获取到实际的接口（这里point拦截到的都是mapper接口）
		Class<?> className = point.getTarget().getClass();
		className = className.getInterfaces()[0];
		// 获得访问的方法名
		String methodName = point.getSignature().getName();
		// 得到方法的参数的类型
		Class[] argClass = ((MethodSignature) point.getSignature()).getParameterTypes();
		// 得到访问的方法对象
		Method method = className.getMethod(methodName, argClass);

		return method;
	}

	/**
	 * 获取当前拦截的包名信息
	 * 
	 * @param point
	 * @return
	 */
	public String getPackageName(JoinPoint point) {
		// 获得当前访问的class，这里获取到的是代理类，需要获取到实际的接口（这里point拦截到的都是mapper接口）
		Class<?> className = point.getTarget().getClass();
		className = className.getInterfaces()[0];

		// 获取包名，需要根据包名获取默认的datasource
		String packageName = className.getName();

		return packageName;
	}
	
}
