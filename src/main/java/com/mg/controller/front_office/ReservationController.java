package com.mg.controller.front_office;

import Annotation.*;
import Model.ModelAndView;
import com.mg.dao.ReservationDAO;
import com.mg.dao.UtilisateurDAO;
import com.mg.dao.VolDAO;
import com.mg.dao.TypeSiegeDAO;
import com.mg.model.Reservation;
import com.mg.model.Utilisateur;
import com.mg.model.Vol;
import com.mg.model.TypeSiege;

import java.util.List;

@Controller
public class ReservationController {
    private final ReservationDAO reservationDAO = new ReservationDAO();
    private final VolDAO volDAO = new VolDAO();
    private final TypeSiegeDAO typeSiegeDAO = new TypeSiegeDAO();
    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();


    @Get
    @Url(road_url = "/reserver")
    public ModelAndView reservationForm(@Param(name = "volId") Integer volId) throws Exception {
        ModelAndView mv = new ModelAndView("/front-office/reservations/form.jsp");
        Vol vol = volDAO.findById(Vol.class, volId);
        List<TypeSiege> typeSieges = typeSiegeDAO.findAll(TypeSiege.class);

        if (vol != null && reservationDAO.isReservationAllowed(vol)) {
            mv.add_data("vol", vol);
            mv.add_data("typeSieges", typeSieges);
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
            @Param(name = "nombrePlaces") Integer nombrePlaces) throws Exception {


        Vol vol = volDAO.findById(Vol.class, volId);

        if (vol != null && reservationDAO.isReservationAllowed(vol)) {
            Reservation reservation = new Reservation();
            reservation.setVol(vol);
            reservation.setTypeSiege(typeSiegeDAO.findById(TypeSiege.class, typeSiegeId));
            reservation.setNombrePlaces(nombrePlaces);

            // TODO: Ajouter la logique pour l'utilisateur connecté
            // reservation.setUtilisateur(utilisateurConnecte);

            reservationDAO.save(reservation);
            return listUserReservations();
        } else {
            ModelAndView mv = new ModelAndView();
            mv.setUrl("/front-office/reservations/form.jsp");
            mv.add_data("error", "La réservation n'a pas pu être effectuée");
            mv.add_data("vol", vol);
            mv.add_data("typeSieges", typeSiegeDAO.findAll(TypeSiege.class));
            return mv;
        }
    }

    @Get
    @Url(road_url = "/mes-reservations")
    public ModelAndView listUserReservations() throws Exception {
        ModelAndView mv = new ModelAndView("/front-office/reservations/list.jsp");
        // TODO: Récupérer l'utilisateur connecté
//        Utilisateur kenny = utilisateurDAO.findById(Utilisateur.class, 1L);
         List<Reservation> reservations =
         reservationDAO.findByUtilisateur(1);
         mv.add_data("reservations", reservations);
        return mv;
    }
}