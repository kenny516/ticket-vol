package com.mg.service;

import com.mg.dao.*;
import com.mg.model.*;

import java.util.List;

public class ReservationService extends AbstractService<Reservation> {
    private final ReservationDAO reservationDAO;
    private final UtilisateurDAO utilisateurDAO;
    private final PlaceVolDAO placeVolDAO;

    public ReservationService() {
        super(new ReservationDAO());
        this.reservationDAO = (ReservationDAO) dao;
        this.utilisateurDAO = new UtilisateurDAO();
        this.placeVolDAO = new PlaceVolDAO();
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
        reservation.setPlaceVol(placeVolDAO.findById(PlaceVol.class, volId));
        reservation.setUtilisateur(utilisateurDAO.findById(Utilisateur.class, utilisateurId));
        reservation.setNombrePlaces(nombrePlaces);
        reservation.setPrix(prix);
        reservation.setValider(true);

        reservationDAO.save(reservation);
        return reservation;
    }

    public Boolean cancelReservation(Integer reservationId) {
        return reservationDAO.cancelReservation(reservationId);
    }

    public List<Reservation> findRecentReservations() {
        return reservationDAO.findRecentReservations();
    }

    public long countActiveReservations() {
        return reservationDAO.countActiveReservations();
    }
}