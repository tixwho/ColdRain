package com.coldrain.database.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class InitSessionFactory {
   /** The single instance of hibernate SessionFactory */
   private static org.hibernate.SessionFactory sessionFactory;
   private InitSessionFactory() {
   }

   public static SessionFactory getInstance() {
       if (sessionFactory == null ||sessionFactory.isClosed()) {
           sessionFactory = new Configuration().configure().buildSessionFactory();
       }
      return sessionFactory;
   }
   

   public static Session getNewSession(){
       return getInstance().openSession();
   }
   
   public static Session getCurrentSession() {
       return getInstance().getCurrentSession();
   }

}
