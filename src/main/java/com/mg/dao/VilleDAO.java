package com.mg.dao;

import com.mg.model.Ville;
import com.mg.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
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
}