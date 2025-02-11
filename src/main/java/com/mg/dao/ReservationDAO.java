package com.mg.dao;

import com.mg.model.Reservation;
import com.mg.model.Parametre;
import com.mg.model.Vol;
import com.mg.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

public class ReservationDAO implements GenericDAO<Reservation> {

    public boolean isReservationAllowed(Vol vol) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            // Get system parameters
            Query<Parametre> paramQuery = session.createQuery("FROM Parametre", Parametre.class);
            Parametre param = paramQuery.setMaxResults(1).uniqueResult();

            if (param == null) {
                return false;
            }

            // Calculate the minimum allowed time before flight
            Calendar minTime = Calendar.getInstance();
            minTime.add(Calendar.HOUR, param.getHeuresMinimumReservation());

            return vol.getDateDepart().after(minTime.getTime());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public boolean canCancelReservation(Reservation reservation) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            Query<Parametre> paramQuery = session.createQuery("FROM Parametre", Parametre.class);
            Parametre param = paramQuery.setMaxResults(1).uniqueResult();

            if (param == null) {
                return false;
            }

            Calendar minTime = Calendar.getInstance();
            minTime.add(Calendar.HOUR, param.getHeuresMinimumAnnulation());

            return reservation.getVol().getDateDepart().after(minTime.getTime());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Reservation> findByVol(Long volId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM Reservation r WHERE r.vol.id = :volId";
            Query<Reservation> query = session.createQuery(hql, Reservation.class);
            query.setParameter("volId", volId);
            return query.list();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Reservation> findByUtilisateur(Long userId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM Reservation r WHERE r.utilisateur.id = :userId ORDER BY r.vol.dateDepart DESC";
            Query<Reservation> query = session.createQuery(hql, Reservation.class);
            query.setParameter("userId", userId);
            return query.list();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public boolean cancelReservation(Long reservationId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            Reservation reservation = session.get(Reservation.class, reservationId);
            if (reservation != null && canCancelReservation(reservation)) {
                reservation.setValider(false);
                session.update(reservation);
                session.getTransaction().commit();
                return true;
            }
            return false;
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

    public List<Reservation> findActiveReservations(Long volId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM Reservation r WHERE r.vol.id = :volId AND r.valider = true";
            Query<Reservation> query = session.createQuery(hql, Reservation.class);
            query.setParameter("volId", volId);
            return query.list();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public long countActiveReservations() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "SELECT COUNT(r) FROM Reservation r " +
                    "WHERE r.valider = true AND r.vol.dateDepart > CURRENT_TIMESTAMP";
            Query<Long> query = session.createQuery(hql, Long.class);
            return query.uniqueResult();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Reservation> findRecentReservations() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM Reservation r " +
                    "WHERE r.valider = true " +
                    "ORDER BY r.vol.dateDepart ASC";
            Query<Reservation> query = session.createQuery(hql, Reservation.class);
            query.setMaxResults(10);
            return query.list();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}