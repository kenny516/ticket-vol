package com.mg.dao;

import com.mg.model.Ville;
import com.mg.model.Vol;
import com.mg.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.Date;
import java.util.List;

public class VilleDAO implements GenericDAO<Ville> {

    public List<Ville> findByNom(String nom) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM Ville v WHERE LOWER(v.nom) LIKE LOWER(:nom)";
            Query<Ville> query = session.createQuery(hql, Ville.class);
            query.setParameter("nom", "%" + nom + "%");
            return query.list();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Ville> findVillesDepartWithVols() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "SELECT DISTINCT v.villeDepart FROM Vol v " +
                    "WHERE v.dateDepart > CURRENT_TIMESTAMP " +
                    "ORDER BY v.villeDepart.nom";
            Query<Ville> query = session.createQuery(hql, Ville.class);
            return query.list();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Ville> findVillesArriveePourVilleDepart(Integer villeDepartId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "SELECT DISTINCT v.villeArrive FROM Vol v " +
                    "WHERE v.villeDepart.id = :villeDepartId " +
                    "AND v.dateDepart > CURRENT_TIMESTAMP " +
                    "ORDER BY v.villeArrive.nom";
            Query<Ville> query = session.createQuery(hql, Ville.class);
            query.setParameter("villeDepartId", villeDepartId);
            return query.list();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Ville> searchVilles(String nomVille) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM Ville v WHERE LOWER(v.nom) LIKE LOWER(:nomVille)";
            Query<Ville> query = session.createQuery(hql, Ville.class);
            query.setParameter("nomVille", "%" + nomVille + "%");
            return query.list();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}