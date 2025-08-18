package com.mg.controller.front_office;

import Annotation.*;
import Annotation.auth.Auth;
import Model.CustomSession;
import Model.ModelAndView;
import com.mg.DTO.VolDTO;
import com.mg.model.*;
import com.mg.service.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.io.InputStream;

@Controller
@Auth(roles = "client")
public class ReservationController {
    private final ReservationService reservationService;
    private final VolService volService;
    private final ParametreService parametreService;
    private final PlaceService placeService;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    private final TypeSiegeService typeSiegeService;
    private final PlaceAleaService placeAleaService;

    public ReservationController() {
        this.reservationService = new ReservationService();
        this.volService = new VolService();
        this.parametreService = new ParametreService();
        this.placeService = new PlaceService();
        this.typeSiegeService = new TypeSiegeService();
        this.placeAleaService = new PlaceAleaService();
    }

    @Get
    @Url(road_url = "/reserver")
    public ModelAndView reservationForm(@Param(name = "volId") Integer volId,@Param(name = "error") String error) throws Exception {
        ModelAndView mv = new ModelAndView("/front-office/reservations/form.jsp");
        Vol vol = volService.findById(Vol.class, volId, "placeVols");
        vol.setTypeSieges(typeSiegeService.findAll(TypeSiege.class));
        if (error != null) {
            mv.add_data("error", error);
        }
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
            @Param(name = "date") String date,
            @Param(name = "nombreAdultes") Integer nombreAdultes,
            @Param(name = "nombreEnfants") Integer nombreEnfants,
            CustomSession session) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setIsRedirect(true);
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");

        modelAndView.setUrl("/ticket-vol/reserver?volId=" + volId + "&error=Vol inconnu");
        try {
            Date dateRes = date != null ? dateFormat.parse(date) : null;
            reservationService.reservationBuild(utilisateur, dateRes, volId, typeSiegeId, nombreEnfants, nombreAdultes);
            modelAndView.setUrl("/ticket-vol/mes-reservations");
        } catch (Exception error) {
            modelAndView.setUrl("/ticket-vol/reserver?volId=" + volId + "&error=" + error.getMessage());
        }
        return modelAndView;
    }

    @Get
    @Url(road_url = "/payer-reservation")
    public ModelAndView payerReservaation(@Param(name = "idReservation") Integer idReservation, CustomSession session) {
        reservationService.payer(idReservation);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setUrl("/ticket-vol/mes-reservations");
        return modelAndView;
    }


    @Get
    @RestApi
    @Url(road_url = "/reguler")
    public String reguler(@Param(name = "date") String date, CustomSession session) throws Exception {
        Date dateRes = date != null ? dateFormat.parse(date) : null;
        assert dateRes != null;
        placeAleaService.refreshButoire(dateRes);
        return "refresh";
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
    public ModelAndView annulationReservation(@Param(name = "idReservation") Integer idReservation,
                                              CustomSession customSession) throws Exception {
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

    @Get
    @Url(road_url = "/reservations/pdf")
    public ModelAndView generatePdf(@Param(name = "id") Integer reservationId, CustomSession session) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setIsRedirect(true);

        // Récupérer l'URL de l'API depuis le fichier de configuration
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
        }
        String apiUrl = properties.getProperty("api.url");
        apiUrl += properties.getProperty("api.reservations.url");

        // Rediriger vers l'API avec l'ID de réservation
        mv.setUrl(apiUrl + "/" + reservationId + "/pdf");
        return mv;
    }
}