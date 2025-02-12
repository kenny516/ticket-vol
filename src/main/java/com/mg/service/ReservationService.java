package com.mg.service;

import com.mg.dao.ReservationDAO;
import com.mg.dao.UtilisateurDAO;
import com.mg.dao.VolDAO;
import com.mg.dao.TypeSiegeDAO;
import com.mg.model.Reservation;
import com.mg.model.Vol;
import com.mg.model.TypeSiege;
import com.mg.model.Utilisateur;
import java.util.List;

public class ReservationService extends AbstractService<Reservation> {
    private final ReservationDAO reservationDAO;
    private final UtilisateurDAO utilisateurDAO;
    private final VolDAO volDAO;
    private final TypeSiegeDAO typeSiegeDAO;

    public ReservationService() {
        super(new ReservationDAO());
        this.reservationDAO = (ReservationDAO) dao;
        this.utilisateurDAO = new UtilisateurDAO();
        this.volDAO = new VolDAO();
        this.typeSiegeDAO = new TypeSiegeDAO();
    }

    public List<Reservation> findByVol(Integer volId) {
        return reservationDAO.findByVol(volId);
    }

    public List<Reservation> findByUtilisateur(Integer userId) {
        return reservationDAO.findByUtilisateur(userId);
    }

    public boolean isReservationAllowed(Vol vol) {
        return reservationDAO.isReservationAllowed(vol);
    }

    public Reservation createReservation(Integer volId, Integer utilisateurId,
            Integer typeSiegeId, Integer nombrePlaces, Double prix) {
        Reservation reservation = new Reservation();
        reservation.setVol(volDAO.findById(Vol.class, volId));
        reservation.setUtilisateur(utilisateurDAO.findById(Utilisateur.class, utilisateurId));
        reservation.setTypeSiege(typeSiegeDAO.findById(TypeSiege.class, typeSiegeId));
        reservation.setNombrePlaces(nombrePlaces);
        reservation.setPrix(prix);
        reservation.setValider(true);

        reservationDAO.save(reservation);
        return reservation;
    }

    public void cancelReservation(Integer reservationId) {
        Reservation reservation = reservationDAO.findById(Reservation.class, reservationId);
        if (reservation != null) {
            reservation.setValider(false);
            reservationDAO.update(reservation);
        }
    }

    public List<Reservation> findRecentReservations() {
        return reservationDAO.findRecentReservations();
    }

    public long countActiveReservations() {
        return reservationDAO.countActiveReservations();
    }
}