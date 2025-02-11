package com.mg.dao;

import com.mg.model.TypeSiege;
import com.mg.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

public class TypeSiegeDAO implements GenericDAO<TypeSiege> {

    public List<TypeSiege> findByAvion(Integer avionId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "SELECT DISTINCT ts FROM TypeSiege ts " +
                    "JOIN ts.places p " +
                    "WHERE p.avion.id = :avionId";
            Query<TypeSiege> query = session.createQuery(hql, TypeSiege.class);
            query.setParameter("avionId", avionId);
            return query.list();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public boolean hasAvailableSeats(Integer typeSiegeId, Integer volId, int requiredSeats) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "SELECT SUM(p.nombre) FROM Place p " +
                    "WHERE p.typeSiege.id = :typeSiegeId " +
                    "AND p.avion.id = (SELECT v.avion.id FROM Vol v WHERE v.id = :volId)";
            Query<Integer> query = session.createQuery(hql, Integer.class);
            query.setParameter("typeSiegeId", typeSiegeId);
            query.setParameter("volId", volId);
            Integer totalSeats = query.uniqueResult();

            // Get total promotional seats already allocated
            String promoHql = "SELECT SUM(p.nbSiege) FROM Promotion p " +
                    "WHERE p.typeSiege.id = :typeSiegeId " +
                    "AND p.vol.id = :volId";
            Query<Integer> promoQuery = session.createQuery(promoHql, Integer.class);
            promoQuery.setParameter("typeSiegeId", typeSiegeId);
            promoQuery.setParameter("volId", volId);
            Integer promoSeats = promoQuery.uniqueResult();

            if (totalSeats == null)
                return false;
            if (promoSeats == null)
                promoSeats = 0;

            return (totalSeats - promoSeats) >= requiredSeats;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<TypeSiege> findAvailableForVol(Integer volId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "SELECT DISTINCT ts FROM TypeSiege ts " +
                    "INNER JOIN Place p ON p.typeSiege = ts " +
                    "INNER JOIN Vol v ON p.avion = v.avion " +
                    "WHERE v.id = :volId";
            Query<TypeSiege> query = session.createQuery(hql, TypeSiege.class);
            query.setParameter("volId", volId);
            return query.list();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<TypeSiege> findWithAvailablePromotions(Integer volId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "SELECT DISTINCT ts FROM TypeSiege ts " +
                    "INNER JOIN Promotion p ON p.typeSiege = ts " +
                    "WHERE p.vol.id = :volId AND p.nbSiege > 0";
            Query<TypeSiege> query = session.createQuery(hql, TypeSiege.class);
            query.setParameter("volId", volId);
            return query.list();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}