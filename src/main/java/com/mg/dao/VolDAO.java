package com.mg.dao;

import com.mg.model.Vol;
import com.mg.model.Ville;
import com.mg.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.Date;
import java.util.List;

public class VolDAO implements GenericDAO<Vol> {

    public List<Vol> searchVols(Ville villeDepart, Ville villeArrive, Date dateDepart, Double maxPrice) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            StringBuilder hql = new StringBuilder("FROM Vol v WHERE 1=1");
            if (villeDepart != null) {
                hql.append(" AND v.villeDepart = :villeDepart");
            }
            if (villeArrive != null) {
                hql.append(" AND v.villeArrive = :villeArrive");
            }
            if (dateDepart != null) {
                hql.append(" AND DATE(v.dateDepart) = DATE(:dateDepart)");
            }
            if (maxPrice != null) {
                hql.append(" AND v.prix <= :maxPrice");
            }

            Query<Vol> query = session.createQuery(hql.toString(), Vol.class);

            if (villeDepart != null) {
                query.setParameter("villeDepart", villeDepart);
            }
            if (villeArrive != null) {
                query.setParameter("villeArrive", villeArrive);
            }
            if (dateDepart != null) {
                query.setParameter("dateDepart", dateDepart);
            }
            if (maxPrice != null) {
                query.setParameter("maxPrice", maxPrice);
            }

            return query.list();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Vol> getAvailableVols(Date fromDate, Date toDate) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM Vol v WHERE v.dateDepart BETWEEN :fromDate AND :toDate " +
                    "AND v.dateDepart > CURRENT_TIMESTAMP ORDER BY v.dateDepart";
            Query<Vol> query = session.createQuery(hql, Vol.class);
            query.setParameter("fromDate", fromDate);
            query.setParameter("toDate", toDate);
            return query.list();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}