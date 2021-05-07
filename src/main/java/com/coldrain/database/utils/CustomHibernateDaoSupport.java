package com.coldrain.database.utils;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

public abstract class CustomHibernateDaoSupport extends HibernateDaoSupport {

    @Autowired
    public void daoFactorySetup(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

}
