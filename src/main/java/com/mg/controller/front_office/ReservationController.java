package com.mg.controller.front_office;

import Annotation.*;
import Model.CustomSession;
import Model.ModelAndView;
import com.mg.DTO.VolDTO;
import com.mg.service.ReservationService;
import com.mg.service.VolService;
import com.mg.service.TypeSiegeService;
import com.mg.service.UtilisateurService;
import com.mg.model.Reservation;
import com.mg.model.Vol;
import com.mg.model.TypeSiege;
import com.mg.model.Utilisateur;
import java.util.List;

@Controller
public class ReservationController {
    private final ReservationService reservationService;
    private final VolService volService;
    private final TypeSiegeService typeSiegeService;
    private final UtilisateurService utilisateurService;

    public ReservationController() {
        this.reservationService = new ReservationService();
        this.volService = new VolService();
        this.typeSiegeService = new TypeSiegeService();
        this.utilisateurService = new UtilisateurService();
    }

    @Get
    @Url(road_url = "/reserver")
    public ModelAndView reservationForm(@Param(name = "volId") Integer volId) throws Exception {
        ModelAndView mv = new ModelAndView("/front-office/reservations/form.jsp");
        Vol vol = volService.findById(Vol.class,volId);
        List<TypeSiege> typeSieges = typeSiegeService.findAll(TypeSiege.class);

        if (vol != null && reservationService.isReservationAllowed(vol)) {
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
            @Param(name = "nombrePlaces") Integer nombrePlaces,
            CustomSession session) throws Exception {

        Vol vol = volService.findById(Vol.class, volId);
        if (vol != null && typeSiegeService.hasAvailableSeats(typeSiegeId, volId, nombrePlaces)) {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
            Double prix = vol.getPrix() * nombrePlaces;





            reservationService.createReservation(volId, utilisateur.getId(), typeSiegeId, nombrePlaces, prix);
            return new ModelAndView("redirect:/mes-reservations");
        }

        return new ModelAndView("redirect:/reserver?volId=" + volId + "&error=true");
    }

    @Get
    @Url(road_url = "/mes-reservations")
    public ModelAndView listUserReservations(CustomSession session) throws Exception {
        ModelAndView mv = new ModelAndView("/front-office/reservations/list.jsp");
        Integer userId = (Integer) session.getAttribute("userId");

        List<Reservation> reservations = reservationService.findByUtilisateur(userId);
        mv.add_data("reservations", reservations);

        return mv;
    }
}