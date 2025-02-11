package com.mg.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.mg.utils.HibernateUtil;
import java.util.List;

public interface GenericDAO<T> {
    default T save(T entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    default T update(T entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    default void delete(T entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    default T findById(Class<T> entityClass, Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(entityClass, id);
        } finally {
            session.close();
        }
    }

    default List<T> findAll(Class<T> entityClass) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("from " + entityClass.getName(), entityClass).list();
        } finally {
            session.close();
        }
    }
}