package org.netcracker.labs.My_models_manager.daos;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.type.StringType;
import org.netcracker.labs.My_models_manager.FormCheckboxes;
import org.netcracker.labs.My_models_manager.HibernateSessionFactoryUtil;
import org.netcracker.labs.My_models_manager.entities.Manufacturer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ManufacturerDao implements DaoInterface<Manufacturer> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManufacturerDao.class);

    @Override
    public List<Manufacturer> getAll() {
        return (List<Manufacturer>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("FROM Manufacturer").list();
    }

    @Override
    public void save(Manufacturer entity) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.save(entity);
            tx.commit();
            LOGGER.info("Manufacturer with id={} was saved", entity.getId());
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error("Can't save Manufacturer");
            LOGGER.error(e.getMessage());
        }
        session.close();
    }

    @Override
    public void save(Manufacturer entity, Long id) {
        entity.setId(id);
        save(entity);
    }

    @Override
    public void update(Manufacturer entity) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.update(entity);
            tx.commit();
            LOGGER.info("Manufacturer {} with id={} was updated", entity.getName(), entity.getId());
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            LOGGER.error("Can't update Manufacturer");
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
            Manufacturer manufacturer = session.get(Manufacturer.class, id);
            session.delete(manufacturer);
            tx.commit();
            LOGGER.info("Manufacturer {} with id={} was deleted", manufacturer.getName(), manufacturer.getId());
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            LOGGER.error("Can't delete Manufacturer");
            LOGGER.error(e.getMessage());
        }
        session.close();
    }

    @Override
    public List<Manufacturer> findAllByName(String name) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        String hql = "FROM Manufacturer WHERE name LIKE ?0";

        Query query = session.createQuery(hql).setParameter(0, "%" + name + "%");
        List<Manufacturer> list = (List<Manufacturer>) query.getResultList();
        session.close();
        LOGGER.info("Found {} manufacturers with name '{}'", list.size(), name);
        return list;
    }

    @Override
    public Manufacturer findById(Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Manufacturer manufacturer = session.get(Manufacturer.class, id);
        session.close();
        if (manufacturer != null)
            LOGGER.info("Manufacturer with id={} was found", id);
        else
            LOGGER.warn("Manufacturer with id {} wasn't found", id);
        return manufacturer;
    }

    @Override
    public void deleteAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            for (Manufacturer manufacturer : getAll())
                session.delete(manufacturer);
            tx.commit();
            LOGGER.info("Manufacturers was deleted");
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            LOGGER.error("Can't delete Manufacturers");
            LOGGER.error(e.getMessage());
        }
        session.close();
    }

    @Override
    public void deleteHighlighted(FormCheckboxes formCheckboxes) throws DataIntegrityViolationException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            for (String str : formCheckboxes.getMultiCheckboxSelectedValues())
                session.delete(findById(Long.parseLong(str)));
            tx.commit();
            LOGGER.info("Highlighted Manufacturers were deleted");
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            LOGGER.error("Can't delete Highlighted Manufacturers");
            LOGGER.error(e.getMessage());
        }
        session.close();
    }
}

