package org.netcracker.labs.My_models_manager.daos;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.netcracker.labs.My_models_manager.FormCheckboxes;
import org.netcracker.labs.My_models_manager.HibernateSessionFactoryUtil;
import org.netcracker.labs.My_models_manager.entities.Manufacturer;
import org.netcracker.labs.My_models_manager.entities.ModelStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public class ModelStatusDao implements DaoInterface<ModelStatus> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelStatusDao.class);

    @Override
    public List<ModelStatus> getAll() {
        return (List<ModelStatus>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("FROM ModelStatus").list();
    }

    @Override
    public void save(ModelStatus entity) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.save(entity);
            tx.commit();
            LOGGER.info("ModelStatus with id={} was saved", entity.getId());
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error("Can't save ModelStatus");
            LOGGER.error(e.getMessage());
        }
        session.close();
    }

    @Override
    public void save(ModelStatus entity, Long id) {
        entity.setId(id);
        save(entity);
    }

    @Override
    public void update(ModelStatus entity) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.update(entity);
            tx.commit();
            LOGGER.info("ModelStatus {} with id={} was updated", entity.getName(), entity.getId());
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            LOGGER.error("Can't update ModelStatus");
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
            ModelStatus modelStatus = session.get(ModelStatus.class, id);
            session.delete(modelStatus);
            tx.commit();
            LOGGER.info("ModelStatus {} with id={} was deleted", modelStatus.getName(), modelStatus.getId());
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            LOGGER.error("Can't delete ModelStatus");
            LOGGER.error(e.getMessage());
        }
        session.close();
    }

    @Override
    public List<ModelStatus> findAllByName(String name) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        String hql = "FROM ModelStatus WHERE name LIKE ?0";

        Query query = session.createQuery(hql).setParameter(0, "%" + name + "%");
        List<ModelStatus> list = (List<ModelStatus>) query.getResultList();
        session.close();
        LOGGER.info("Found {} ModelStatuses with name '{}'", list.size(), name);
        return list;
    }

    @Override
    public ModelStatus findById(Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        ModelStatus modelStatus = session.get(ModelStatus.class, id);
        session.close();
        if (modelStatus != null)
            LOGGER.info("ModelStatus with id={} was found", id);
        else
            LOGGER.warn("ModelStatus with id {} wasn't found", id);
        return modelStatus;
    }

    @Override
    public void deleteAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            for (ModelStatus modelStatus : getAll())
                session.delete(modelStatus);
            tx.commit();
            LOGGER.info("ModelStatuses were deleted");
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            LOGGER.error("Can't delete ModelStatuses");
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
            LOGGER.info("Highlighted ModelStatuses were deleted");
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            LOGGER.error("Can't delete Highlighted ModelStatuses");
            LOGGER.error(e.getMessage());
        }
        session.close();
    }
}

