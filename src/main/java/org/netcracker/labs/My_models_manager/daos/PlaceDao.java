package org.netcracker.labs.My_models_manager.daos;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.netcracker.labs.My_models_manager.FormCheckboxes;
import org.netcracker.labs.My_models_manager.HibernateSessionFactoryUtil;
import org.netcracker.labs.My_models_manager.entities.Manufacturer;
import org.netcracker.labs.My_models_manager.entities.Place;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlaceDao implements DaoInterface<Place>{
    private static final Logger LOGGER = LoggerFactory.getLogger(PlaceDao.class);

    @Override
    public List<Place> getAll() {
        return (List<Place>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("FROM Place").list();
    }

    @Override
    public void save(Place entity) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.save(entity);
            tx.commit();
            LOGGER.info("Place with id={} was saved", entity.getId());
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error("Can't save Place");
            LOGGER.error(e.getMessage());
        }
        session.close();
    }

    @Override
    public void save(Place entity, Long id) {
        entity.setId(id);
        save(entity);
    }

    @Override
    public void update(Place entity) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.update(entity);
            tx.commit();
            LOGGER.info("Place {} with id={} was updated", entity.getName(), entity.getId());
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            LOGGER.error("Can't update Place");
            LOGGER.error(e.getMessage());
        }
        session.close();
    }

    @Override
    public void delete(Long id) throws DataIntegrityViolationException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Place place = session.get(Place.class, id);
            session.delete(place);
            tx.commit();
            LOGGER.info("Place {} with id={} was deleted", place.getName(), place.getId());
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            LOGGER.error("Can't delete Place");
            LOGGER.error(e.getMessage());
        }
        session.close();
    }

    @Override
    public List<Place> findAllByName(String name) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        String hql = "FROM Place WHERE name LIKE ?0";

        Query query = session.createQuery(hql).setParameter(0, "%" + name + "%");
        List<Place> list = (List<Place>) query.getResultList();
        session.close();
        LOGGER.info("Found {} Places with name '{}'", list.size(), name);
        return list;
    }

    @Override
    public Place findById(Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Place place = session.get(Place.class, id);
        session.close();
        if (place != null)
            LOGGER.info("Place with id={} was found", id);
        else
            LOGGER.warn("Place with id {} wasn't found", id);
        return place;
    }

    @Override
    public void deleteAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            for (Place place : getAll())
                session.delete(place);
            tx.commit();
            LOGGER.info("Places was deleted");
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            LOGGER.error("Can't delete Places");
            LOGGER.error(e.getMessage());
        }
        session.close();
    }

    @Override
    public void deleteHighlighted(FormCheckboxes formCheckboxes) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            for (String str : formCheckboxes.getMultiCheckboxSelectedValues())
                session.delete(findById(Long.parseLong(str)));
            tx.commit();
            LOGGER.info("Highlighted Places were deleted");
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            LOGGER.error("Can't delete Highlighted Places");
            LOGGER.error(e.getMessage());
        }
        session.close();
    }
}

