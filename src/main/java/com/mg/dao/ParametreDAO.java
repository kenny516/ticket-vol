package com.mg.dao;

import com.mg.model.Parametre;
import com.mg.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class ParametreDAO implements GenericDAO<Parametre> {

    public Parametre findFirst() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Parametre> query = session.createQuery("FROM Parametre", Parametre.class);
            query.setMaxResults(1);
            return query.uniqueResult();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}