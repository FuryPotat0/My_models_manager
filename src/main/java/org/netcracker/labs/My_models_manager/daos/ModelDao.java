package org.netcracker.labs.My_models_manager.daos;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.netcracker.labs.My_models_manager.FormCheckboxes;
import org.netcracker.labs.My_models_manager.HibernateSessionFactoryUtil;
import org.netcracker.labs.My_models_manager.entities.Manufacturer;
import org.netcracker.labs.My_models_manager.entities.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ModelDao implements DaoInterface<Model> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelDao.class);

    @Override
    public List<Model> getAll() {
        return (List<Model>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("FROM Model").list();
    }

    @Override
    public void save(Model entity) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.save(entity);
            tx.commit();
            LOGGER.info("Model with id={} was saved", entity.getId());
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error("Can't save Model");
            LOGGER.error(e.getMessage());
        }
        session.close();
    }

    @Override
    public void save(Model entity, Long id) {
        entity.setId(id);
        save(entity);
    }

    @Override
    public void update(Model entity) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.update(entity);
            tx.commit();
            LOGGER.info("Model {} with id={} was updated", entity.getName(), entity.getId());
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            LOGGER.error("Can't update Model");
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
            Model model = session.get(Model.class, id);
            session.delete(model);
            tx.commit();
            LOGGER.info("Model {} with id={} was deleted", model.getName(), model.getId());
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            LOGGER.error("Can't delete Model");
            LOGGER.error(e.getMessage());
        }
        session.close();
    }

    @Override
    public List<Model> findAllByName(String name) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        String hql = "FROM Model WHERE name LIKE ?0";

        Query query = session.createQuery(hql).setParameter(0, "%" + name + "%");
        List<Model> list = (List<Model>) query.getResultList();
        session.close();
        LOGGER.info("Found {} Models with name '{}'", list.size(), name);
        return list;
    }

    @Override
    public Model findById(Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Model model = session.get(Model.class, id);
        session.close();
        if (model != null)
            LOGGER.info("Model with id={} was found", id);
        else
            LOGGER.warn("Model with id {} wasn't found", id);
        return model;
    }

    @Override
    public void deleteAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            for (Model model : getAll())
                session.delete(model);
            tx.commit();
            LOGGER.info("Models was deleted");
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            LOGGER.error("Can't delete Models");
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
            LOGGER.info("Highlighted Models were deleted");
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            LOGGER.error("Can't delete Highlighted Models");
            LOGGER.error(e.getMessage());
        }
        session.close();
    }
}

