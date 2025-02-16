package com.mg.dao;

import com.mg.service.TransactionService;
import com.mg.service.exception.ServiceException;
import java.util.List;

public interface GenericDAO<T> {
    default T findById(Class<T> clazz, Integer id) {
        try {
            return TransactionService.executeInTransaction(session -> session.get(clazz, id));
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche par ID", e);
        }
    }

    default List<T> findAll(Class<T> clazz) {
        try {
            return TransactionService.executeInTransaction(
                    session -> session.createQuery("FROM " + clazz.getSimpleName(), clazz).list());
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