package com.mg.dao;

import com.mg.model.Utilisateur;
import com.mg.utils.HibernateUtil;
import org.hibernate.Session;
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
}
