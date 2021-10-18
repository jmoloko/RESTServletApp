package com.milk.restapp.dao.implementation;

import com.milk.restapp.dao.FileDAO;
import com.milk.restapp.model.File;
import com.milk.restapp.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * @author Jack Milk
 */
public class FileDAOImpl implements FileDAO {

    @Override
    public List<File> getAll() {
        try (Session session = HibernateUtil.sessionFactory.openSession()) {
            return session.createQuery("SELECT f FROM File f LEFT JOIN FETCH f.events ").list();
        }
    }

    @Override
    public File getById(Integer id) {
        try (Session session = HibernateUtil.sessionFactory.openSession()) {
            return session.get(File.class, id);
        }
    }

    @Override
    public File save(File file) {
        try (Session session = HibernateUtil.sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(file);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getById(file.getId());
    }

    @Override
    public File update(File file) {
        try (Session session = HibernateUtil.sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(file);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getById(file.getId());
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
