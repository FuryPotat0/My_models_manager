package org.netcracker.labs.My_models_manager;


import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.netcracker.labs.My_models_manager.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateSessionFactoryUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateSessionFactoryUtil.class);
    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Manufacturer.class);
                configuration.addAnnotatedClass(Room.class);
                configuration.addAnnotatedClass(Place.class);
                configuration.addAnnotatedClass(Storage.class);
                configuration.addAnnotatedClass(Status.class);
                configuration.addAnnotatedClass(Model.class);
//                configuration.addAnnotatedClass(SuperEntity.class);

                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception e) {
                LOGGER.error(e.toString());
                LOGGER.error(e.getMessage());
            }
        }
        return sessionFactory;
    }
}

