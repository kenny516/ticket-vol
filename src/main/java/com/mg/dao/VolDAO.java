package com.mg.dao;

import com.mg.model.Vol;
import com.mg.model.Ville;
import com.mg.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

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

    public List<Vol> searchVolsAdvanced(Ville villeDepart, Ville villeArrive,
            Date dateDebut, Date dateFin, Double prixMin, Double prixMax) {
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
            if (dateDebut != null && dateFin != null) {
                hql.append(" AND v.dateDepart BETWEEN :dateDebut AND :dateFin");
            } else if (dateDebut != null) {
                hql.append(" AND v.dateDepart >= :dateDebut");
            } else if (dateFin != null) {
                hql.append(" AND v.dateDepart <= :dateFin");
            }
            if (prixMin != null) {
                hql.append(" AND v.prix >= :prixMin");
            }
            if (prixMax != null) {
                hql.append(" AND v.prix <= :prixMax");
            }

            hql.append(" ORDER BY v.dateDepart");

            Query<Vol> query = session.createQuery(hql.toString(), Vol.class);

            if (villeDepart != null) {
                query.setParameter("villeDepart", villeDepart);
            }
            if (villeArrive != null) {
                query.setParameter("villeArrive", villeArrive);
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

    public List<Vol> searchVols(Integer villeDepartId, Integer villeArriveId,
            Date dateDebut, Date dateFin,
            Double prixMin, Double prixMax) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            StringBuilder hql = new StringBuilder("FROM Vol v WHERE 1=1");
            List<String> conditions = new ArrayList<>();

            if (villeDepartId != null) {
                conditions.add("v.villeDepart.id = :villeDepartId");
            }
            if (villeArriveId != null) {
                conditions.add("v.villeArrive.id = :villeArriveId");
            }
            if (dateDebut != null) {
                conditions.add("v.dateDepart >= :dateDebut");
            }
            if (dateFin != null) {
                conditions.add("v.dateDepart <= :dateFin");
            }
            if (prixMin != null) {
                conditions.add("v.prix >= :prixMin");
            }
            if (prixMax != null) {
                conditions.add("v.prix <= :prixMax");
            }

            if (!conditions.isEmpty()) {
                hql.append(" AND ").append(String.join(" AND ", conditions));
            }

            hql.append(" ORDER BY v.dateDepart");

            Query<Vol> query = session.createQuery(hql.toString(), Vol.class);

            if (villeDepartId != null) {
                query.setParameter("villeDepartId", villeDepartId);
            }
            if (villeArriveId != null) {
                query.setParameter("villeArriveId", villeArriveId);
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
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Vol> findVolsDisponibles(Integer villeDepartId, Integer villeArriveId, Date dateDepart) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            String hql = "FROM Vol v WHERE v.villeDepart.id = :villeDepartId " +
                    "AND v.villeArrive.id = :villeArriveId " +
                    "AND DATE(v.dateDepart) = DATE(:dateDepart) " +
                    "AND v.dateDepart > CURRENT_TIMESTAMP " +
                    "ORDER BY v.dateDepart";

            Query<Vol> query = session.createQuery(hql, Vol.class);
            query.setParameter("villeDepartId", villeDepartId);
            query.setParameter("villeArriveId", villeArriveId);
            query.setParameter("dateDepart", dateDepart);

            return query.list();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Vol> findUpcomingFlights() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            String hql = "SELECT DISTINCT v FROM Vol v " +
                    "LEFT JOIN FETCH v.reservations " +
                    "WHERE v.dateDepart > CURRENT_TIMESTAMP " +
                    "ORDER BY v.dateDepart ASC";
            Query<Vol> query = session.createQuery(hql, Vol.class);
            List<Vol> vols = query.setMaxResults(5).list();

            if (!vols.isEmpty()) {
                String promotionsHql = "SELECT DISTINCT v FROM Vol v " +
                        "LEFT JOIN FETCH v.promotions " +
                        "WHERE v IN :vols";
                Query<Vol> promotionsQuery = session.createQuery(promotionsHql, Vol.class);
                promotionsQuery.setParameter("vols", vols);
                vols = promotionsQuery.list();
            }
            return vols;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    public List<Vol> findUpcomingFlightsById(Integer id) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            String hql = "SELECT DISTINCT v FROM Vol v " +
                    "LEFT JOIN FETCH v.reservations " +
                    "WHERE v.id = "+id +
                    " ORDER BY v.dateDepart ASC";
            Query<Vol> query = session.createQuery(hql, Vol.class);
            List<Vol> vols = query.setMaxResults(5).list();

            if (!vols.isEmpty()) {
                String promotionsHql = "SELECT DISTINCT v FROM Vol v " +
                        "LEFT JOIN FETCH v.promotions " +
                        "WHERE v IN :vols";
                Query<Vol> promotionsQuery = session.createQuery(promotionsHql, Vol.class);
                promotionsQuery.setParameter("vols", vols);
                vols = promotionsQuery.list();
            }
            return vols;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}