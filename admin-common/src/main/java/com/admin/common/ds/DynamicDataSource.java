package com.admin.common.ds;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源切换
 * @author xiamh
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		// TODO Auto-generated method stub
		return getDataSourceType();
	}
	
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	/**
	 * 设置数据源
	 * @param dataSourceType
	 */
	public static void setDataSourceType(String dataSourceType) {
		contextHolder.set(dataSourceType);
	}
	
	/**
	 * 获取数据源
	 * @return
	 */
	public static String getDataSourceType() {
		return contextHolder.get();
	}
	
	/**
	 * 清空当前数据源
	 */
	public static void clearDataSourceType() {
		contextHolder.remove();
	}
	
}
