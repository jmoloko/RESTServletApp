package com.milk.restapp.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;

/**
 * @author Jack Milk
 */
public class HibernateUtil {

    public static final SessionFactory sessionFactory = initSessionFactory();

    private static SessionFactory initSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation Filed! " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }



}
