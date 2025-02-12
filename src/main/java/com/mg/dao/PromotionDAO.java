package com.mg.dao;

import com.mg.model.Promotion;
import com.mg.model.TypeSiege;
import com.mg.model.Vol;
import com.mg.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

public class PromotionDAO implements GenericDAO<Promotion> {

    public List<Promotion> findByVol(Integer volId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM Promotion p WHERE p.vol.id = :volId";
            Query<Promotion> query = session.createQuery(hql, Promotion.class);
            query.setParameter("volId", volId);
            return query.list();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Promotion> findByVolAndTypeSiege(Vol vol, TypeSiege typeSiege) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM Promotion p WHERE p.vol = :vol AND p.typeSiege = :typeSiege";
            Query<Promotion> query = session.createQuery(hql, Promotion.class);
            query.setParameter("vol", vol);
            query.setParameter("typeSiege", typeSiege);
            return query.list();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Promotion> findActivePromotions(Long volId, Long typeSiegeId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM Promotion p WHERE p.vol.id = :volId " +
                    "AND p.typeSiege.id = :typeSiegeId " +
                    "AND p.nbSiege > 0";
            Query<Promotion> query = session.createQuery(hql, Promotion.class);
            query.setParameter("volId", volId);
            query.setParameter("typeSiegeId", typeSiegeId);
            return query.list();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void updateNbSiege(Long promotionId, Integer nbSiege) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            String hql = "UPDATE Promotion p SET p.nbSiege = :nbSiege WHERE p.id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("nbSiege", nbSiege);
            query.setParameter("id", promotionId);

            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Integer getTotalPromotionalSeats(Vol vol, TypeSiege typeSiege) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "SELECT SUM(p.nbSiege) FROM Promotion p " +
                    "WHERE p.vol = :vol AND p.typeSiege = :typeSiege";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("vol", vol);
            query.setParameter("typeSiege", typeSiege);
            Long result = query.uniqueResult();
            return result != null ? result.intValue() : 0;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}