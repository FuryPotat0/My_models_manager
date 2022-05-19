package org.netcracker.labs.My_models_manager.daos;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.netcracker.labs.My_models_manager.FormCheckboxes;
import org.netcracker.labs.My_models_manager.HibernateSessionFactoryUtil;
import org.netcracker.labs.My_models_manager.entities.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StatusDao implements DaoInterface<Status> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatusDao.class);

    @Override
    public List<Status> getAll() {
        return (List<Status>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("FROM Status").list();
    }

    @Override
    public void save(Status entity) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.save(entity);
            tx.commit();
            LOGGER.info("Status with id={} was saved", entity.getId());
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error("Can't save Status");
            LOGGER.error(e.getMessage());
        }
        session.close();
    }

    @Override
    public void save(Status entity, Long id) {
        entity.setId(id);
        save(entity);
    }

    @Override
    public void update(Status entity) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.update(entity);
            tx.commit();
            LOGGER.info("Status {} with id={} was updated", entity.getName(), entity.getId());
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            LOGGER.error("Can't update Status");
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
            Status status = session.get(Status.class, id);
            session.delete(status);
            tx.commit();
            LOGGER.info("Status {} with id={} was deleted", status.getName(), status.getId());
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            LOGGER.error("Can't delete Status");
            LOGGER.error(e.getMessage());
        }
        session.close();
    }

    @Override
    public List<Status> findAllByName(String name) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        String hql = "FROM Status WHERE name LIKE ?0";

        Query query = session.createQuery(hql).setParameter(0, "%" + name + "%");
        List<Status> list = (List<Status>) query.getResultList();
        session.close();
        LOGGER.info("Found {} Statuses with name '{}'", list.size(), name);
        return list;
    }

    @Override
    public Status findById(Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Status status = session.get(Status.class, id);
        session.close();
        if (status != null)
            LOGGER.info("Status with id={} was found", id);
        else
            LOGGER.warn("Status with id {} wasn't found", id);
        return status;
    }

    @Override
    public void deleteAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            for (Status status : getAll())
                session.delete(status);
            tx.commit();
            LOGGER.info("Statuses were deleted");
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            LOGGER.error("Can't delete Statuses");
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
            LOGGER.info("Highlighted Statuses were deleted");
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            LOGGER.error("Can't delete Highlighted Statuses");
            LOGGER.error(e.getMessage());
        }
        session.close();
    }
}

