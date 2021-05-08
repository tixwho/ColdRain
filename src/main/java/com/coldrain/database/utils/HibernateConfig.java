package com.coldrain.database.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:coldraindb.properties")
public class HibernateConfig {

    @Value("${coldrain.db.url}")
    private String dburl;

    @Value("${coldrain.db.showsql}")
    private boolean showsql;

    @Value("${coldrain.db.hbm2ddl.auto.update}")
    private boolean autoUpdateStruct;


    @Value("${coldrain.db.hbm2ddl.auto.create}")
    private boolean autoFormatDatabase;

    @Value("${coldrain.db.hibernate.enable_lazy_load_no_trans}")
    private boolean lazyloadnotrans;

    public String getDburl() {
        return dburl;
    }

    public boolean getShowsql() {
        return showsql;
    }

    public boolean getAutoUpdateStruct() {
        return autoUpdateStruct;
    }

    public boolean getAutoFormatDatabase() {
        return autoFormatDatabase;
    }

    public boolean getLazyloadnotrans() {
        return lazyloadnotrans;
    }

}
