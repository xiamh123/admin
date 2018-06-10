package com.admin.common.conf;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.admin.common.enums.DataSourceEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.alibaba.druid.pool.DruidDataSource;
import com.admin.common.ds.DynamicDataSource;

/**
 * 数据源配置，包括单个数据源和多数据源配置
 * @author xiamh
 *
 */
@Configuration
public class DataSourceConfig {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired  
    private Environment env;
	
	/** 默认数据源 */
	@Value("${sprng.default.ds}")
	public String defaultDs;
	
    //durid连接池配置信息
    @Value("${spring.datasource.initialSize}")
    private int initialSize;
    @Value("${spring.datasource.minIdle}")
	private int minIdle;
    @Value("${spring.datasource.maxActive}")
	private int maxActive;
    @Value("${spring.datasource.maxWait}")
	private int maxWait;
    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
	private int timeBetweenEvictionRunsMillis;
    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
	private int minEvictableIdleTimeMillis;
    @Value("${spring.datasource.validationQuery}")
	private String validationQuery;
    @Value("${spring.datasource.testWhileIdle}")
	private boolean testWhileIdle;
    @Value("${spring.datasource.testOnBorrow}")
	private boolean testOnBorrow;
    @Value("${spring.datasource.testOnReturn}")
	private boolean testOnReturn;
    @Value("${spring.datasource.poolPreparedStatements}")
	private boolean poolPreparedStatements;
    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
	private int maxPoolPreparedStatementPerConnectionSize;
    @Value("${spring.datasource.filters}")
	private String filters;
    @Value("${spring.datasource.connectionProperties}")
	private String connectionProperties;
	
	@Autowired  
    ApplicationContext context;
	
	/**
	 * 动态数据源: 通过AOP在不同数据源之间动态切换增加数据源则需要先在DataSourceEnum枚举中增加枚举值
	 * 
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean(name = "dataSource")
	public DataSource dataSource() throws Exception {
		DynamicDataSource dynamicDataSource = new DynamicDataSource();
		dynamicDataSource.setDefaultTargetDataSource(createDataSource(defaultDs));
		Map<Object, Object> dsMap = new HashMap<Object, Object>();
		
		/** 根据反射动态设置数据源 */
		Class clazz = this.getClass();
		DataSourceEnum[] dsArr = DataSourceEnum.values();
		logger.debug("---------------------------------------------------------------------");
		logger.debug("---------------------------------------------------------------------");
		for(DataSourceEnum ds : dsArr) {
			dsMap.put(ds.getDs(), clazz.getMethod("createDataSource", String.class).invoke(this,ds.getDs()));
		}
		dynamicDataSource.setTargetDataSources(dsMap);
		logger.debug("---------------------------------------------------------------------");
		logger.debug("---------------------------------------------------------------------");
		
		return dynamicDataSource;
	}
	
	/**
	 * 创建一个druid数据源连接池
	 * @param url
	 * @param user
	 * @param password
	 * @param driverClass
	 * @return
	 * @throws Exception 
	 */
	public DataSource createDataSource(String ds) throws Exception {
		DruidDataSource dataSource = new DruidDataSource();
        
		String url = env.getProperty("spring.datasource."+ds+".url");
		String user = env.getProperty("spring.datasource."+ds+".username");
		String password = env.getProperty("spring.datasource."+ds+".password");
		String driverClass = env.getProperty("spring.datasource."+ds+".driver-class-name");
		
		dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClass);
        
        // configuration
		dataSource.setInitialSize(initialSize);
		dataSource.setMinIdle(minIdle);
		dataSource.setMaxActive(maxActive);
		dataSource.setMaxWait(maxWait);
		dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		dataSource.setValidationQuery(validationQuery);
		dataSource.setTestWhileIdle(testWhileIdle);
		dataSource.setTestOnBorrow(testOnBorrow);
		dataSource.setTestOnReturn(testOnReturn);
		dataSource.setPoolPreparedStatements(poolPreparedStatements);
		dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
		try {
			dataSource.setFilters(filters);
		} catch (SQLException e) {
			System.err.println("druid configuration initialization filter: " + e);
		}
		dataSource.setConnectionProperties(connectionProperties);
		
		if(StringUtils.isBlank(url) || StringUtils.isBlank(user) || 
				StringUtils.isBlank(password) || StringUtils.isBlank(driverClass)) {
			logger.error("=====数据源[{}]未配置相关信息，创建失败:url-{},user-{},password-{},driver-{}==================",ds,url,user,StringUtils.isBlank(password)?password:"******",driverClass);
		}else {
			logger.info("======创建数据源[{}]完成===================================================",ds);
		}
		
        return dataSource;
	}

}
