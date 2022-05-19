package org.netcracker.labs.My_models_manager.daos;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.netcracker.labs.My_models_manager.FormCheckboxes;
import org.netcracker.labs.My_models_manager.HibernateSessionFactoryUtil;
import org.netcracker.labs.My_models_manager.entities.Manufacturer;
import org.netcracker.labs.My_models_manager.entities.Storage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StorageDao implements DaoInterface<Storage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StorageDao.class);

    @Override
    public List<Storage> getAll() {
        return (List<Storage>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("FROM Storage").list();
    }

    @Override
    public void save(Storage entity) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.save(entity);
            tx.commit();
            LOGGER.info("Storage with id={} was saved", entity.getId());
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error("Can't save Storage");
            LOGGER.error(e.getMessage());
        }
        session.close();
    }

    @Override
    public void save(Storage entity, Long id) {
        entity.setId(id);
        save(entity);
    }

    @Override
    public void update(Storage entity) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.update(entity);
            tx.commit();
            LOGGER.info("Storage {} with id={} was updated", entity.getName(), entity.getId());
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            LOGGER.error("Can't update Storage");
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
            Storage storage = session.get(Storage.class, id);
            session.delete(storage);
            tx.commit();
            LOGGER.info("Storage {} with id={} was deleted", storage.getName(), storage.getId());
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            LOGGER.error("Can't delete Storage");
            LOGGER.error(e.getMessage());
        }
        session.close();
    }

    @Override
    public List<Storage> findAllByName(String name) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        String hql = "FROM Storage WHERE name LIKE ?0";

        Query query = session.createQuery(hql).setParameter(0, "%" + name + "%");
        List<Storage> list = (List<Storage>) query.getResultList();
        session.close();
        LOGGER.info("Found {} Storages with name '{}'", list.size(), name);
        return list;
    }

    @Override
    public Storage findById(Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Storage storage = session.get(Storage.class, id);
        session.close();
        if (storage != null)
            LOGGER.info("Storage with id={} was found", id);
        else
            LOGGER.warn("Storage with id {} wasn't found", id);
        return storage;
    }

    @Override
    public void deleteAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            for (Storage storage : getAll())
                session.delete(storage);
            tx.commit();
            LOGGER.info("Storages was deleted");
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            LOGGER.error("Can't delete Storages");
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
            LOGGER.info("Highlighted Storages were deleted");
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            LOGGER.error("Can't delete Highlighted Storages");
            LOGGER.error(e.getMessage());
        }
        session.close();
    }
}

