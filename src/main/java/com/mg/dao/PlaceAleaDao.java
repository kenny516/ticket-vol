package com.mg.dao;

import com.mg.model.PlaceAlea;
import com.mg.utils.HibernateUtil;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class PlaceAleaDao extends BaseDao<PlaceAlea> {

    public PlaceAleaDao() {
        super(PlaceAlea.class);
    }

    public List<PlaceAlea> findByVolAndTypeSiege(Integer idVol, Integer idTypeSiege) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM PlaceAlea p WHERE p.vol.id = :idVol AND p.typeSiege.id = :idTypeSiege order by dateprom asc ";
            var query = session.createQuery(hql, PlaceAlea.class);
            query.setParameter("idVol", idVol);
            query.setParameter("idTypeSiege", idTypeSiege);
            return query.list();
        }
    }

    public void updatePlaceAlea(PlaceAlea placeAlea) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(placeAlea);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String validateReservation() {
        return "";
    }

    public List<PlaceAlea> findByVol(Integer volId) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM PlaceAlea p WHERE p.vol.id = :volId";
            var query = session.createQuery(hql, PlaceAlea.class);
            query.setParameter("volId", volId);
            return query.list();
        }
    }

    public List<PlaceAlea> findByTypeSiege(Integer typeSiegeId) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM PlaceAlea p WHERE p.typeSiege.id = :typeSiegeId";
            var query = session.createQuery(hql, PlaceAlea.class);
            query.setParameter("typeSiegeId", typeSiegeId);
            return query.list();
        }
    }

    public List<PlaceAlea> findByDateRange(Date dateDebut, Date dateFin) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM PlaceAlea p WHERE p.dateprom BETWEEN :dateDebut AND :dateFin";
            var query = session.createQuery(hql, PlaceAlea.class);
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            return query.list();
        }
    }

    public List<PlaceAlea> findByDateBefore(Date dateFin) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM PlaceAlea p WHERE p.dateprom <= :dateFin";
            var query = session.createQuery(hql, PlaceAlea.class);
            query.setParameter("dateFin", dateFin);
            return query.list();
        }
    }


    public List<PlaceAlea> searchWithFilters(Integer volId, Integer typeSiegeId,
            Date dateDebut, Date dateFin,
            Double prixMin, Double prixMax) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            StringBuilder hql = new StringBuilder("FROM PlaceAlea p WHERE 1=1");

            if (volId != null) {
                hql.append(" AND p.vol.id = :volId");
            }
            if (typeSiegeId != null) {
                hql.append(" AND p.typeSiege.id = :typeSiegeId");
            }
            if (dateDebut != null) {
                hql.append(" AND p.dateprom >= :dateDebut");
            }
            if (dateFin != null) {
                hql.append(" AND p.dateprom <= :dateFin");
            }
            if (prixMin != null) {
                hql.append(" AND p.prix >= :prixMin");
            }
            if (prixMax != null) {
                hql.append(" AND p.prix <= :prixMax");
            }

            var query = session.createQuery(hql.toString(), PlaceAlea.class);

            if (volId != null) {
                query.setParameter("volId", volId);
            }
            if (typeSiegeId != null) {
                query.setParameter("typeSiegeId", typeSiegeId);
            }
            if (dateDebut != null) {
                query.setParameter("dateDebut", dateDebut);
            }
            if (dateFin != null) {
                query.setParameter("dateFin", dateFin);
            }
            if (prixMin != null) {
                query.setParameter("prixMin", prixMin);
            }
            if (prixMax != null) {
                query.setParameter("prixMax", prixMax);
            }

            return query.list();
        }
    }

}
