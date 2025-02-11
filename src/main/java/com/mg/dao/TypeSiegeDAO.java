package com.mg.dao;

import com.mg.model.TypeSiege;
import com.mg.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

public class TypeSiegeDAO implements GenericDAO<TypeSiege> {

    public List<TypeSiege> findByAvion(Long avionId) {
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

    public boolean hasAvailableSeats(Long typeSiegeId, Long volId, int requiredSeats) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "SELECT SUM(p.nombre) FROM Place p " +
                    "WHERE p.typeSiege.id = :typeSiegeId " +
                    "AND p.avion.id = (SELECT v.avion.id FROM Vol v WHERE v.id = :volId)";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("typeSiegeId", typeSiegeId);
            query.setParameter("volId", volId);
            Long totalSeats = query.uniqueResult();

            // Get total promotional seats already allocated
            String promoHql = "SELECT SUM(p.nbSiege) FROM Promotion p " +
                    "WHERE p.typeSiege.id = :typeSiegeId " +
                    "AND p.vol.id = :volId";
            Query<Long> promoQuery = session.createQuery(promoHql, Long.class);
            promoQuery.setParameter("typeSiegeId", typeSiegeId);
            promoQuery.setParameter("volId", volId);
            Long promoSeats = promoQuery.uniqueResult();

            if (totalSeats == null)
                return false;
            if (promoSeats == null)
                promoSeats = 0L;

            return (totalSeats - promoSeats) >= requiredSeats;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}