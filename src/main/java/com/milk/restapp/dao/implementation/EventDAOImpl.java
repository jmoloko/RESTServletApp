package com.milk.restapp.dao.implementation;

import com.milk.restapp.dao.EventDAO;
import com.milk.restapp.model.Event;
import com.milk.restapp.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * @author Jack Milk
 */
public class EventDAOImpl implements EventDAO {

    @Override
    public List<Event> getAll() {
        try (Session session = HibernateUtil.sessionFactory.openSession()) {
            return session.createQuery("SELECT e FROM Event e LEFT JOIN FETCH e.user LEFT JOIN FETCH e.file").list();
        }
    }

    @Override
    public Event getById(Integer id) {
        try (Session session = HibernateUtil.sessionFactory.openSession()) {
            return session.get(Event.class, id);
        }
    }

    public List<Event> getEventsByUserId(Integer id) {
        try (Session session = HibernateUtil.sessionFactory.openSession()){
            Query query = session.createSQLQuery("SELECT e.* FROM event e WHERE e.user = :id").addEntity(Event.class);
//            Query query = session.createQuery("SELECT e FROM Event e WHERE e.user = :id");
            return query.setParameter("id", id).list();
        }
    }

    @Override
    public Event save(Event event) {
        try (Session session = HibernateUtil.sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(event);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getById(event.getId());
    }

    @Override
    public Event update(Event event) {
        try (Session session = HibernateUtil.sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(event);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getById(event.getId());
    }

    @Override
    public void deleteById(Integer id) {
        try (Session session = HibernateUtil.sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(getById(id));
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
