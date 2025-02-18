package com.mg.dao;

import com.mg.service.TransactionService;
import com.mg.service.exception.ServiceException;
import org.hibernate.query.Query;

import java.util.List;

public interface GenericDAO<T> {
    default T findById(Class<T> clazz, Integer id, String... fetchAssociations) {
        try {
            return TransactionService.executeInTransaction(session -> {
                StringBuilder hql = new StringBuilder("FROM " + clazz.getSimpleName() + " e");
                for (String association : fetchAssociations) {
                    hql.append(" LEFT JOIN FETCH e.").append(association);
                }
                hql.append(" WHERE e.id = :id");

                Query<T> query = session.createQuery(hql.toString(), clazz);
                query.setParameter("id", id);

                return query.getSingleResult();
            });
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche par ID", e);
        }
    }

    default List<T> findAll(Class<T> clazz,String... fetchAssociations) {
        try {
            return TransactionService.executeInTransaction(
                    session -> {
                        StringBuilder hql = new StringBuilder("FROM " + clazz.getSimpleName() + " e");
                        for (String association : fetchAssociations) {
                            hql.append(" LEFT JOIN FETCH e.").append(association);
                        }
                        Query<T> query = session.createQuery(hql.toString(), clazz);
                        return query.getResultList();
                    });
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération de tous les éléments", e);
        }
    }

    default void save(T entity) {
        try {
            TransactionService.executeInTransactionWithoutResult(session -> {
                session.save(entity);
                return null;
            });
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la sauvegarde", e);
        }
    }

    default void update(T entity) {
        try {
            TransactionService.executeInTransactionWithoutResult(session -> {
                session.update(entity);
                return null;
            });
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour", e);
        }
    }

    default void delete(T entity) {
        try {
            TransactionService.executeInTransactionWithoutResult(session -> {
                session.delete(entity);
                return null;
            });
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression", e);
        }
    }
}