package com.admin.common.conf;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 配置mybatis连接信息，使用多数据源
 * @author xiamh
 *
 */
//@Configuration
//@MapperScan(basePackages = {"com.kpmdp.**.mapper*","com.kayak.kpms.**.mapper*"}, sqlSessionFactoryRef = "sqlSessionFactory")
public class MybatisDbConfig {

    @Autowired
    @Qualifier("dataSource")
    private DataSource ds;

    
    /**
     * 这里全部使用mybatis-autoconfigure 已经自动加载的资源。不手动指定
     * 配置文件和mybatis-boot的配置文件同步
     * @return
     */
//    @Bean
//    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean() {
//        MybatisSqlSessionFactoryBean mybatisPlus = new MybatisSqlSessionFactoryBean();
//        mybatisPlus.setDataSource(ds);
//        mybatisPlus.setVfs(SpringBootVFS.class);
//        return mybatisPlus;
//    }

//    @Bean
//    public SqlSessionFactory sqlSessionFactory() throws Exception {
//        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
//        factoryBean.setDataSource(ds); 
//        org.apache.ibatis.session.Configuration configuration=new org.apache.ibatis.session.Configuration();
//        configuration.setUseGeneratedKeys(true);				//使用jdbc的getGeneratedKeys获取数据库自增主键值
//        configuration.setUseColumnLabel(true);				//使用列别名替换列名 select user as User
//        configuration.setMapUnderscoreToCamelCase(true);		//-自动使用驼峰命名属性映射字段   userId    user_id
//        factoryBean.setConfiguration(configuration);
//        return factoryBean.getObject();
//    }

//    @Bean
//    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
//        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory()); // 使用上面配置的Factory
//        return template;
//    }
}
