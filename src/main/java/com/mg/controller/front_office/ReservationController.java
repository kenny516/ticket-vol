package com.mg.controller.front_office;

import Annotation.*;
import Annotation.auth.Auth;
import Model.CustomSession;
import Model.ModelAndView;
import com.mg.DTO.VolDTO;
import com.mg.model.*;
import com.mg.service.ReservationService;
import com.mg.service.VolService;
import com.mg.service.TypeSiegeService;
import com.mg.service.UtilisateurService;

import java.util.List;

@Controller
@Auth(roles = "client")
public class ReservationController {
    private final ReservationService reservationService;
    private final VolService volService;

    public ReservationController() {
        this.reservationService = new ReservationService();
        this.volService = new VolService();
    }

    @Get
    @Url(road_url = "/reserver")
    public ModelAndView reservationForm(@Param(name = "volId") Integer volId) throws Exception {
        ModelAndView mv = new ModelAndView("/front-office/reservations/form.jsp");
        Vol vol = volService.findById(Vol.class, volId,"placeVols");

        if (vol != null && reservationService.isReservationAllowed(vol)) {
            mv.add_data("vol", vol);
        } else {
            mv.add_data("error", "La réservation n'est plus possible pour ce vol");
        }
        return mv;
    }

    @Post
    @Url(road_url = "/vols/reserver")
    public ModelAndView createReservation(
            @Param(name = "volId") Integer volId,
            @Param(name = "typeSiegeId") Integer typeSiegeId,
            @Param(name = "nombrePlaces") Integer nombrePlaces,
            CustomSession session) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setIsRedirect(true);
        Vol vol = volService.getVolFullById(volId);
        if (vol != null) {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
            Double prixInitial = 0.0;
            VolDTO volDTO = volService.getVolsDTOById(vol);
            for (PlaceVol place : vol.getPlaceVols()) {
                if (place.getTypeSiege().getId() == typeSiegeId) {
                    if (volDTO.getPlacesDisponibles().get(place.getTypeSiege().getDesignation()) < nombrePlaces) {
                        modelAndView.setUrl("/ticket-vol/reserver?volId=" + volId + "&error=Nombre de place inssuffisant");
                        return modelAndView;
                    }
                    prixInitial = place.getPrix();
                    break;
                }
            }
            // verification de place et promotion
            Double prix = volService.promotionAvailable(vol, typeSiegeId, nombrePlaces, prixInitial);

            reservationService.createReservation(volId, utilisateur.getId(), typeSiegeId, nombrePlaces, prix);
            modelAndView.setUrl("/ticket-vol/mes-reservations");
            return modelAndView;
        }
        modelAndView.setUrl("/ticket-vol/reserver?volId=" + volId + "&error=vol inconnue");
        return modelAndView;
    }

    @Get
    @Url(road_url = "/mes-reservations")
    public ModelAndView listUserReservations(CustomSession session) throws Exception {
        ModelAndView mv = new ModelAndView("/front-office/reservations/list.jsp");
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");

        List<Reservation> reservations = reservationService.findByUtilisateur(utilisateur.getId());

        mv.add_data("reservations", reservations);

        return mv;
    }

    @Get
    @Url(road_url = "/annuler-reservation")
    public ModelAndView annulationReservation(@Param(name = "idReservation") Integer idReservation, CustomSession customSession) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setIsRedirect(true);
        Utilisateur utilisateur = (Utilisateur) customSession.getAttribute("user");
        Boolean process = false;
        if (utilisateur != null) {
            process = reservationService.cancelReservation(idReservation);
        }
        if (process) {
            modelAndView.setUrl("/ticket-vol/mes-reservations");
            return modelAndView;
        }
        modelAndView.setUrl("/ticket-vol/mes-reservations?error=Erreur lors de l'annulation de la réservation");
        return modelAndView;

    }
}