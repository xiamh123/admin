package com.admin.common.codegenerator;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * <p>
 * 代码生成器，目前仅支持mysql
 * </p>
 *
 * @author xiamh
 * @date 2017-07-17
 */
public class TableGenerator {

    static Logger logger = LoggerFactory.getLogger(TableGenerator.class);

    static Properties prop = null;

    /**
     * 初始化参数
     *
     * @author xiamh
     * @date 2017年7月17日 下午4:27:20
     */
    @SuppressWarnings("deprecation")
    public static void init() {

        logger.info("**********开始初始化参数**********");
        //获取项目根目录
        String rootPath = TableGenerator.class.getClassLoader().getResource("").getPath();
        rootPath = URLDecoder.decode(rootPath.substring(0, rootPath.lastIndexOf("target")));
        System.out.println("rootPath : " + rootPath);

        try {
            prop = PropertiesLoaderUtils.loadAllProperties("conf/init.properties");
            if (prop != null) {
                logger.info("加载配置文件成功");
                prop.setProperty("javaFileOutDir", rootPath + "src/main/java/");                   //java文件输出目录
                //prop.setProperty("xmlFileOutDir", rootPath + "src/main/resources/mapper/pass/"); //xml文件输出目录
                prop.setProperty("xmlFileOutDir", rootPath + prop.getProperty("xml.url"));           //xml文件输出目录(读取配置文件)
            }
        } catch (IOException e) {
            // TODO Auto-generated catch  block
            e.printStackTrace();
            logger.info("加载配置文件失败");
        }
        logger.info("**********完成参数初始化**********");

        logger.info("**********开始生成代码**********");
        execute();
        logger.info("**********代码生成成功**********");
    }

    /**
     * 反向生成代码
     *
     * @author xiamh
     * @date 2017年7月17日 下午4:27:30
     */
    public static void execute() {

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator().setGlobalConfig(
                // 全局配置
                new GlobalConfig()
                        .setOutputDir(prop.getProperty("javaFileOutDir"))//输出目录
                        .setFileOverride(false)            //是否覆盖文件
                        .setActiveRecord(false)            //开启activeRecord 模式
                        .setEnableCache(false)            //XML二级缓存
                        .setBaseResultMap(true)            //XMLResultMap
                        .setBaseColumnList(true)        //XMLcolumList
                        .setAuthor("caoyong")                //作者
                        .setOpen(false)                    //是否打开输出目录
                        .setXmlName("%sMapper")            //自定义Xml文件名格式
        ).setDataSource(
                // 数据源配置
                new DataSourceConfig()
                        .setDbType(getDbType(prop.getProperty("dbType", "mysql")))// 数据库类型
                        .setDriverName(prop.getProperty("datasource.driver-class-name"))
                        .setUsername(prop.getProperty("datasource.username"))
                        .setPassword(prop.getProperty("datasource.password"))
                        .setUrl(prop.getProperty("datasource.url"))

                //自定义数据库表字段类型转换，暂时用不到 xiamh
//                        .setTypeConvert(new MySqlTypeConvert() {
                // 自定义数据库表字段类型转换【可选】
//                            @Override
//                            public DbColumnType processTypeConvert(String fieldType) {
//                                System.out.println("转换类型：" + fieldType);
                // if ( fieldType.toLowerCase().contains( "tinyint" ) ) {
                //    return DbColumnType.BOOLEAN;
                // }
//                                return super.processTypeConvert(fieldType);
//                            }
//                        })

        ).setStrategy(
                // 策略配置
                new StrategyConfig()
                        .setNaming(NamingStrategy.underline_to_camel)                        //表名生成策略
                        .setInclude(getTableArr(prop.getProperty("table.include", "")))    //需要生成的表
                        .setExclude(getTableArr(prop.getProperty("table.exclude", "")))    //排除生成的表
                        .setRestControllerStyle(true)                                        //使用@RestController
        ).setPackageInfo(
                // 包配置
                new PackageConfig()
                        .setParent(prop.getProperty("pack.url", "com"))                    //自定义包路径
                        .setController("controller")                                        //这里是控制器包名，默认 web
                        .setEntity("model")                                                    //这里是javabean包名，默认entity
        ).setCfg(
                // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值,可以在模版中自定义参数用
                new InjectionConfig() {
                    @Override
                    public void initMap() {
                        Map<String, Object> map = new HashMap<>();
                        map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                        this.setMap(map);
                    }
                }.setFileOutConfigList(Collections.<FileOutConfig>singletonList(new FileOutConfig("/templates/mapper.xml.vm") {
                    // 自定义输出文件目录
                    @Override
                    public String outputFile(TableInfo tableInfo) {
                        return prop.getProperty("xmlFileOutDir") + tableInfo.getXmlName() + ".xml";
                    }
                }))
        ).setTemplate(
                // 关闭默认 xml 生成，调整生成 至 根目录
                new TemplateConfig().setXml(null)
                // 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
                // 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
                //.setController(null)
        );

        // 执行生成
        mpg.execute();

    }

    /**
     * 将数据库类型转为枚举值
     *
     * @param dbType
     * @return
     * @author xiamh
     * @date 2017年7月17日 下午4:46:19
     */
    public static DbType getDbType(String dbType) {

        if (StringUtils.isBlank(dbType)) {
            return DbType.MYSQL;
        }

        switch (dbType) {
            case "mysql":
                return DbType.MYSQL;
            case "oracle":
                return DbType.ORACLE;
            default:
                return DbType.MYSQL;
        }
    }

    /**
     * 将配置的包含/剔除表名转为数组
     *
     * @param str
     * @return
     * @author xiamh
     * @date 2017年7月18日 上午10:55:43
     */
    public static String[] getTableArr(String str) {

        if (StringUtils.isBlank(str)) {
            return null;
        }
        return str.split(",");

    }


    /**
     * <p>
     * MySQL 生成演示
     * </p>
     */
    public static void main(String[] args) {
        init();
    }

}
