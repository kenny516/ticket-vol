package com.mg.dao;

import com.mg.model.Promotion;
import com.mg.model.TypeSiege;
import com.mg.model.Vol;
import com.mg.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

public class PromotionDAO implements GenericDAO<Promotion> {

    public List<Promotion> findByVol(Long volId) {
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