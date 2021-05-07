package com.coldrain.database.utils;

import com.alibaba.druid.pool.DruidDataSource;
import java.util.Properties;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HibernateDatabaseConfig {

    @Bean(name = "crDatasource")
    @Primary
    DataSource createDataSource(@Autowired HibernateConfig hibernateConfig) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:sqlite:"+hibernateConfig.getDburl());
        System.out.println("DBURL:"+hibernateConfig.getDburl());
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setInitialSize(5);
        dataSource.setMaxActive((10));
        dataSource.setMaxWait(60000);
        dataSource.setMinIdle(1);
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(300000);
        dataSource.setTestWhileIdle(true);
        dataSource.setValidationQuery("SELECT '1' from sqlite_master");
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        //can not integrate with sqlite
        dataSource.setPoolPreparedStatements(false);
        //dataSource.setMaxOpenPreparedStatements(20);
        dataSource.setAsyncInit(true);
        //DEVELOPING
        dataSource.setBreakAfterAcquireFailure(true);

        return dataSource;

    }


    @Bean
    LocalSessionFactoryBean createSessionFactory(@Autowired DataSource dataSource, @Autowired HibernateConfig hibernateConfig) {
        Properties props = new Properties();
        if(hibernateConfig.getAutoFormatDatabase()){
            props.setProperty("hibernate.hbm2ddl.auto","create");
        }else if(hibernateConfig.getAutoUpdateStruct()){
            props.setProperty("hibernate.hbm2ddl.auto","update");
        }
        if(hibernateConfig.getLazyloadnotrans()) {
            props.setProperty("hibernate.enable_lazy_load_no_trans", "true");
        }
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLiteDialect");
        props.setProperty("hibernate.show_sql", "true");
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        // 扫描指定的package获取所有entity class:
        sessionFactoryBean.setPackagesToScan("com.coldrain.database.models");
        sessionFactoryBean.setHibernateProperties(props);
        return sessionFactoryBean;
    }

    @Bean(name="crHibernateTemplate")
    @Primary
    HibernateTemplate createHibernateTemplate(@Autowired SessionFactory sessionFactory) {
        return new HibernateTemplate(sessionFactory);
    }

    @Bean(name="crTxManager")
    @Primary
    PlatformTransactionManager createTxManager(@Autowired SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }

}
