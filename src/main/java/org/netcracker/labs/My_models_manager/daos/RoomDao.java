package org.netcracker.labs.My_models_manager.daos;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.netcracker.labs.My_models_manager.FormCheckboxes;
import org.netcracker.labs.My_models_manager.HibernateSessionFactoryUtil;
import org.netcracker.labs.My_models_manager.entities.Manufacturer;
import org.netcracker.labs.My_models_manager.entities.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoomDao implements DaoInterface<Room> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomDao.class);

    @Override
    public List<Room> getAll() {
        return (List<Room>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("FROM Room").list();
    }

    @Override
    public void save(Room entity) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.save(entity);
            tx.commit();
            LOGGER.info("Room with id={} was saved", entity.getId());
        } catch (HibernateException e){
            if (tx != null){
                tx.rollback();
            }
            LOGGER.error("Can't save Room");
            LOGGER.error(e.getMessage());
        }
        session.close();
    }

    @Override
    public void save(Room entity, Long id) {
        entity.setId(id);
        save(entity);
    }

    @Override
    public void update(Room entity) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try{
            tx = session.beginTransaction();
            session.update(entity);
            tx.commit();
            LOGGER.info("Room {} with id={} was updated", entity.getName(), entity.getId());
        } catch (HibernateException e){
            if (tx != null)
                tx.rollback();
            LOGGER.error("Can't update Room");
            LOGGER.error(e.getMessage());
        }
        session.close();
    }

    @Override
    public void delete(Long id) throws DataIntegrityViolationException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try{
            tx = session.beginTransaction();
            Room room = session.get(Room.class, id);
            session.delete(room);
            tx.commit();
            LOGGER.info("Room {} with id={} was deleted", room.getName(), room.getId());
        } catch (HibernateException e){
            if (tx != null)
                tx.rollback();
            LOGGER.error("Can't delete Room");
            LOGGER.error(e.getMessage());
        }
        session.close();
    }

    @Override
    public List<Room> findAllByName(String name) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        String hql = "FROM Room WHERE name LIKE ?0";

        Query query = session.createQuery(hql).setParameter(0, "%" + name + "%");
        List<Room> list = (List<Room>) query.getResultList();
        session.close();
        LOGGER.info("Found {} manufacturers with name '{}'", list.size(), name);
        return list;
    }

    @Override
    public Room findById(Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Room room = session.get(Room.class, id);
        session.close();
        if (room != null)
            LOGGER.info("Room with id={} was found", id);
        else
            LOGGER.warn("Room with id {} wasn't found", id);
        return room;
    }

    @Override
    public void deleteAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try{
            tx = session.beginTransaction();
            for (Room room : getAll())
                session.delete(room);
            tx.commit();
            LOGGER.info("Rooms was deleted");
        } catch (HibernateException e){
            if (tx != null)
                tx.rollback();
            LOGGER.error("Can't delete Rooms");
            LOGGER.error(e.getMessage());
        }
        session.close();
    }

    @Override
    public void deleteHighlighted(FormCheckboxes formCheckboxes)  throws DataIntegrityViolationException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            for (String str : formCheckboxes.getMultiCheckboxSelectedValues())
                session.delete(findById(Long.parseLong(str)));
            tx.commit();
            LOGGER.info("Highlighted Rooms was deleted");
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            LOGGER.error("Can't delete Highlighted Rooms");
            LOGGER.error(e.getMessage());
        }
        session.close();
    }
}

