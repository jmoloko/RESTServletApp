package com.milk.restapp.dao.implementation;

import com.milk.restapp.dao.UserDAO;
import com.milk.restapp.model.User;
import com.milk.restapp.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * @author Jack Milk
 */
public class UserDAOImpl implements UserDAO {

    @Override
    public List<User> getAll() {
        try (Session session = HibernateUtil.sessionFactory.openSession()) {
            return session.createQuery("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.events").list();
        }
    }

    @Override
    public User getById(Integer id) {
        try (Session session = HibernateUtil.sessionFactory.openSession()) {
            Query query = session.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.events WHERE u.id = :id");
            query.setParameter("id", id);
            return (User) query.getSingleResult();
        }
    }

    @Override
    public User save(User user) {
        try (Session session = HibernateUtil.sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getById(user.getId());
    }

    @Override
    public User update(User user) {
        try (Session session = HibernateUtil.sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getById(user.getId());
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
