package com.mg.service;

import com.mg.DTO.VolDTO;
import com.mg.dao.*;
import com.mg.model.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class ReservationService extends AbstractService<Reservation> {
    private final ReservationDAO reservationDAO;

    private final UtilisateurService utilisateurService;
    private final PlaceVolService placeVolService;
    private final VolService volService;
    private final ParametreService parametreService;
    private final PlaceAleaService placeAleaService;

    public ReservationService() {
        super(new ReservationDAO());
        this.reservationDAO = new ReservationDAO();
        this.utilisateurService = new UtilisateurService();
        this.placeVolService = new PlaceVolService();
        this.volService = new VolService();
        this.parametreService = new ParametreService();
        this.placeAleaService = new PlaceAleaService();
    }

    public List<Reservation> findByUtilisateur(Integer userId) {
        return reservationDAO.findByUtilisateur(userId);
    }

    public boolean isReservationAllowed(Vol vol) {
        return reservationDAO.isReservationAllowed(vol);
    }

    public Boolean cancelReservation(Integer reservationId) {
        return reservationDAO.cancelReservation(reservationId);
    }

    public Reservation createReservation(Date date, Integer placeAleaVolId, Integer utilisateurId, Integer nombrePlacesTotal, Double prix, Integer nombreAdultes, Integer nombreEnfants) {
        Reservation reservation = new Reservation();
        PlaceAlea placeAlea = placeAleaService.findById(PlaceAlea.class, placeAleaVolId);

        reservation.setPlaceAlea(placeAlea);
        reservation.setUtilisateur(utilisateurService.findById(Utilisateur.class, utilisateurId));
        reservation.setNombrePlaces(nombrePlacesTotal);
        reservation.setNombreAdultes(nombreAdultes);
        reservation.setNombreEnfants(nombreEnfants);
        reservation.setPrix(prix);
        reservation.setValider(true);
        // change localdate to date
        reservation.setDateReservation(date);

        reservation.setPayer(false);

        reservationDAO.save(reservation);

        if (placeAlea.getRestant() >= reservation.getNombrePlaces()) {
            placeAlea.setReserver(placeAlea.getReserver() + reservation.getNombrePlaces());
            placeAleaService.update(placeAlea);
        } else {
            throw new RuntimeException("Pas assez de places disponibles pour valider la réservation");
        }

        return reservation;
    }

    public Reservation reservationBuild(Utilisateur user, Date date, Integer volId, Integer typeSiegeId, Integer nbPlaceEnfant, Integer nbPlaceAdulte) throws Exception {
        int nombrePlacesTotal = nbPlaceEnfant + nbPlaceAdulte;
        Integer placeAleaId = null;
        int placeNon = nombrePlacesTotal;

        double prixTotal = 0.0;
        Vol vol = volService.getVolFullById(volId);
        if (vol != null) {
            VolDTO volDTO = volService.getVolsDTOById(vol);
            // Todo use the alea add date and
            List<PlaceAlea> placeAleas = placeAleaService.findByVolAndTypeSiege(volId, typeSiegeId);
            System.out.println("taille  " + placeAleas.size());
            for (PlaceAlea placeAlea : placeAleas) {
                if (!placeAlea.getDateprom().before(date) && (placeAlea.getRestant()-placeAlea.getReserver()) >= nombrePlacesTotal) {
                    placeAleaId = placeAlea.getId();
                    System.out.println("place restant" + placeAlea.getRestant());
                    System.out.println("place non " + placeNon);
                    int placeTrouve = Math.min(nombrePlacesTotal, placeAlea.getRestant());
                    prixTotal = placeTrouve * placeAlea.getPrix();
                    placeNon = placeNon - placeTrouve;
                    break;
                }
            }
            if (placeAleas.isEmpty()) {
                throw new Exception("Aucune place disponible pour le vol sélectionné avec le type de siège choisi");
            }
            if (placeNon > 0) {
                throw new Exception("Pas assez de places disponibles pour le vol sélectionné");
            }
//            Parametre reductionEnfant = parametreService.findById(Parametre.class, "reduc_enfant");
//            double tauxReductionEnfant = reductionEnfant != null ? reductionEnfant.getValeur() : 0.0;

            //Double prixAdultes = volService.promotionProcess(vol, typeSiegeId, nbPlaceAdulte, prixInitial);

//            // Prix enfants (avec réduction enfant)
//            Double prixEnfants = volService.promotionProcess(vol, typeSiegeId, nbPlaceEnfant, prixInitial);
//            prixEnfants = prixEnfants * (1 - tauxReductionEnfant / 100);

//            Double prixTotal = prixAdultes + prixEnfants;
            //Todo a determiner prixTotal
            // = nombrePlacesTotal
            return createReservation(date, placeAleaId, user.getId(), nombrePlacesTotal, prixTotal, nbPlaceAdulte, nbPlaceEnfant);

        }
        throw new Exception("Vol non reconnue ou n'existe pas");
    }

    public void payer(Integer idReservation) {
        Reservation reservation = reservationDAO.findById(Reservation.class, idReservation);
        PlaceAlea placeAlea = placeAleaService.findById(PlaceAlea.class, reservation.getPlaceAlea().getId());
        if (placeAlea != null && placeAlea.getRestant() >= reservation.getNombrePlaces()) {
            reservation.setPayer(true);
            reservationDAO.update(reservation);

            placeAlea.setRestant(placeAlea.getRestant() - reservation.getNombrePlaces());

            placeAlea.setReserver(placeAlea.getReserver() - reservation.getNombrePlaces());

            placeAleaService.update(placeAlea);

        } else {
            throw new RuntimeException("Pas assez de places disponibles pour valider la réservation");
        }

    }


}