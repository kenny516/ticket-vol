package com.mg.dao;

import com.mg.model.Avion;
import com.mg.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class AvionDAO implements GenericDAO<Avion> {

    // On peut ajouter des méthodes spécifiques pour la recherche d'avions si
    // nécessaire
    public List<Avion> findAvailableAvions(Long villeId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM Avion a WHERE a.villeActuelle.id = :villeId " +
                    "AND a.estDisponible = true";
            Query<Avion> query = session.createQuery(hql, Avion.class);
            query.setParameter("villeId", villeId);
            return query.list();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}