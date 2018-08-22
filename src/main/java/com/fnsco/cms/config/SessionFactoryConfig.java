//package com.fnsco.cms.config;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.core.io.support.ResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.transaction.annotation.TransactionManagementConfigurer;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableTransactionManagement
//public class SessionFactoryConfig implements TransactionManagementConfigurer {
//    /** * mybatis 配置路径 */
//    private static String MYBATIS_CONFIG   = "mybatis.xml";
//
//    @Autowired
//    private DataSource    dataSource;
//
//    private String        typeAliasPackage = "net.fnsco";
//
//    /**
//    *创建sqlSessionFactoryBean 实例
//    * 并且设置configtion 如驼峰命名.等等
//    * 设置mapper 映射路径
//    * 设置datasource数据源
//    * @return
//    * @throws Exception
//    */
//    @Bean(name = "sqlSessionFactory")
//    public SqlSessionFactoryBean createSqlSessionFactoryBean() throws Exception {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:mapper/master/**/*.xml"));
//
//        /** 设置mybatis configuration 扫描路径 */
//        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(MYBATIS_CONFIG));
//        /** 设置datasource */
//        sqlSessionFactoryBean.setDataSource(dataSource);
//        /** 设置typeAlias 包xml中sql文件扫描路径 */
//        //domain起别名。
//        sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasPackage);
//        return sqlSessionFactoryBean;
//    }
//
//    @Bean
//    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//
//    @Bean
//    @Override
//    public PlatformTransactionManager annotationDrivenTransactionManager() {
//        return new DataSourceTransactionManager(dataSource);
//    }
//}
