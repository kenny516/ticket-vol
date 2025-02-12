package com.mg.dao;

import com.mg.model.Utilisateur;
import com.mg.utils.HibernateUtil;
import com.mg.service.TransactionService;
import com.mg.service.exception.ServiceException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class UtilisateurDAO implements GenericDAO<Utilisateur> {

    public Utilisateur findByNomAndPrenom(String nom, String prenom) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM Utilisateur u WHERE u.nom = :nom AND u.prenom = :prenom";
            Query<Utilisateur> query = session.createQuery(hql, Utilisateur.class);
            query.setParameter("nom", nom);
            query.setParameter("prenom", prenom);
            query.setMaxResults(1);
            return query.uniqueResult();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Utilisateur> searchUtilisateurs(String searchTerm) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM Utilisateur u WHERE LOWER(u.nom) LIKE LOWER(:searchTerm) " +
                    "OR LOWER(u.prenom) LIKE LOWER(:searchTerm)";
            Query<Utilisateur> query = session.createQuery(hql, Utilisateur.class);
            query.setParameter("searchTerm", "%" + searchTerm + "%");
            return query.list();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Utilisateur findByUsername(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Utilisateur> query = session.createQuery(
                    "FROM Utilisateur WHERE pseudo = :username",
                    Utilisateur.class);
            query.setParameter("username", username);
            return query.uniqueResult();
        }
    }

    public Utilisateur findByPseudoAndPassword(String pseudo, String motDePasse) {
        try {
            return TransactionService.executeInTransaction(session -> {
                String hql = "FROM Utilisateur u WHERE u.pseudo = :pseudo AND u.motDePasse = :motDePasse";
                Query<Utilisateur> query = session.createQuery(hql, Utilisateur.class);
                query.setParameter("pseudo", pseudo);
                query.setParameter("motDePasse", motDePasse);
                return query.uniqueResult();
            });
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de l'authentification", e);
        }
    }

    public void save(Utilisateur utilisateur) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(utilisateur);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
