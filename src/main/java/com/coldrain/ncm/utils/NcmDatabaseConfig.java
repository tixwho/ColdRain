package com.coldrain.ncm.utils;

import java.io.File;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class NcmDatabaseConfig {

    @Bean(name = "ncmDatasource")
    DataSource createDataSource(@Autowired NcmConfig ncmConfig) {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.sqlite.JDBC");
        ds.setUrl("jdbc:sqlite:" + new File(ncmConfig.getNcmCacheDirPath(), "webdb.dat")
            .getAbsolutePath());
        return ds;
    }

    @Bean(name = "ncmJdbcTemplate")
    JdbcTemplate createJdbcTemplate(@Autowired @Qualifier("ncmDatasource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "ncmTxManager")
    TransactionManager createTxManager(
        @Autowired @Qualifier("ncmDatasource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
