package org.netcracker.labs.My_models_manager.daos;

import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.netcracker.labs.My_models_manager.HibernateSessionFactoryUtil;
import org.netcracker.labs.My_models_manager.entities.Manufacturer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@NoArgsConstructor
public class ManufacturerDao implements DaoInterface<Manufacturer> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManufacturerDao.class);

    @Override
    public List<Manufacturer> getAll() throws HibernateException {
        return (List<Manufacturer>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("FROM Manufacturer").list();
    }

    @Override
    public void save(Manufacturer entity) throws HibernateException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.save(entity);
            tx.commit();
            LOGGER.info("Manufacturer with id={} was saved", entity.getId());
        } catch (HibernateException e){
            if (tx != null){
                tx.rollback();
            }
            LOGGER.error("Can't save Manufacturer");
            LOGGER.error(e.getMessage());
        }
        session.close();
    }

    @Override
    public void save(Manufacturer entity, Long id) throws HibernateException {
        entity.setId(id);
        save(entity);
    }

    @Override
    public void update(Manufacturer entity) throws HibernateException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try{
            tx = session.beginTransaction();
            session.update(entity);
            tx.commit();
            LOGGER.info("Manufacturer {} with id={} was updated",entity.getName(), entity.getId());
        } catch (HibernateException e){
            if (tx != null)
                tx.rollback();
            LOGGER.error("Can't update Manufacturer");
            LOGGER.error(e.getMessage());
        }
        session.close();
    }

    @Override
    public void delete(Long id) throws DataIntegrityViolationException {

    }

    @Override
    public List<Manufacturer> findAllByName(String name) {
        return null;
    }

    @Override
    public Manufacturer findById(Long id) {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}

