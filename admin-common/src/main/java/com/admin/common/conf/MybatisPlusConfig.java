package com.admin.common.conf;

import com.baomidou.mybatisplus.spring.MybatisMapperRefresh;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;

/**
 * MybatisPlus配置
 * @author xiamh
 * @date 2017年9月11日 下午7:19:10
 */
@Configuration
@MapperScan(basePackages = {"com.admin.*.mapper*"})   //mapper类映射路径
public class MybatisPlusConfig {
	
	/** mybatis-plus 分页拦截器 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		paginationInterceptor.setLocalPage(true);
		return paginationInterceptor;
	}
	
	/**
	 * mybatis-plus性能分析插件，用于输出每条 SQL 语句及其执行时间，建议只在开发环境使用
	 * @return
	 */
//	@Bean
//	public PerformanceInterceptor performanceInterceptor(){
//		PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
//		performanceInterceptor.setFormat(true);
////		performanceInterceptor.setMaxTime(100000);
//		return performanceInterceptor;
//	}

	/**
	 * XML热加载
	 *
	 * @author hepeng
	 * @date 2018/3/29 11:54
	 */
//	@Bean
//	public MybatisMapperRefresh getMybatisMapperRefresh(SqlSessionFactory sqlSessionFactory) throws Exception {
//		MybatisMapperRefresh mybatisMapperRefresh =
//				new MybatisMapperRefresh(sqlSessionFactory,10,20,true);
//		return mybatisMapperRefresh;
//	}
}
