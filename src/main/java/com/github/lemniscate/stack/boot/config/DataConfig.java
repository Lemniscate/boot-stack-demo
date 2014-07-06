package com.github.lemniscate.stack.boot.config;

import com.github.lemniscate.stack.boot.DemoApplication;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.dialect.HSQLDialect;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate3.support.OpenSessionInViewInterceptor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @Author dave 4/28/14 4:47 PM
 */
@Configuration
public class DataConfig {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
//                .addScript("classpath:schema.sql")
//                 .addScript("classpath:test-data.sql")
                .build();
    }

    @Bean
    public OpenSessionInViewInterceptor openSessionInViewInterceptor(SessionFactory sessionFactory){
        OpenSessionInViewInterceptor result = new OpenSessionInViewInterceptor();
        result.setSessionFactory(sessionFactory);
        return result;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter vendorAdapter){
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan( DemoApplication.class.getPackage().getName() + ".model");
        factory.setDataSource(dataSource);
        factory.setJpaProperties(getJpaProperties());
        return factory;
    }

    @Bean
    public SessionFactory sessionFactory(HibernateEntityManagerFactory emf){
        return emf.getSessionFactory();
    }

    @Bean
    public HibernateJpaVendorAdapter jpaVendorAdapter(){
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setDatabasePlatform(HSQLDialect.class.getName());
        vendorAdapter.setShowSql(true);
        return vendorAdapter;
    }

    private Properties getJpaProperties() {
        Properties props = new Properties();
        props.put("hibernate.ejb.naming_strategy", ImprovedNamingStrategy.class.getName());
        props.put("hibernate.enable_lazy_load_no_trans", "true");
        props.put("jadira.usertype.autoRegisterUserTypes", "true");
        return props;
    }

    @Bean
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean bean){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(bean.getObject());
        return transactionManager;
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

}
