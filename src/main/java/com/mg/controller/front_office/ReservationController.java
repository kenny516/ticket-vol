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
import com.mg.service.ParametreService;
import com.mg.service.PlaceService;

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

    public ReservationController() {
        this.reservationService = new ReservationService();
        this.volService = new VolService();
        this.parametreService = new ParametreService();
        this.placeService = new PlaceService();
    }

    @Get
    @Url(road_url = "/reserver")
    public ModelAndView reservationForm(@Param(name = "volId") Integer volId) throws Exception {
        ModelAndView mv = new ModelAndView("/front-office/reservations/form.jsp");
        Vol vol = volService.findById(Vol.class, volId, "placeVols");

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
            @Param(name = "nombreAdultes") Integer nombreAdultes,
            @Param(name = "nombreEnfants") Integer nombreEnfants,
            CustomSession session) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setIsRedirect(true);
        Vol vol = volService.getVolFullById(volId);

        if (vol != null) {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
            Double prixInitial = 0.0;
            int nombrePlacesTotal = nombreAdultes + nombreEnfants;
            VolDTO volDTO = volService.getVolsDTOById(vol);
            Integer placeVolId = null;

            for (PlaceVol placeVol : vol.getPlaceVols()) {
                // Utiliser la nouvelle structure pour accéder au type de siège via l'objet
                // Place
                if (placeVol.getPlace().getTypeSiege().getId().equals(typeSiegeId)) {
                    // Obtenir la désignation du type de siège via la nouvelle structure
                    String designation = placeVol.getPlace().getTypeSiege().getDesignation();
                    if (volDTO.getPlacesDisponibles().get(designation) < nombrePlacesTotal) {
                        modelAndView
                                .setUrl("/ticket-vol/reserver?volId=" + volId + "&error=Nombre de places insuffisant");
                        return modelAndView;
                    }
                    prixInitial = placeVol.getPrix();
                    placeVolId = placeVol.getId();
                    break;
                }
            }

            if (placeVolId == null) {
                modelAndView.setUrl("/ticket-vol/reserver?volId=" + volId + "&error=Type de siège non trouvé");
                return modelAndView;
            }

            Parametre reductionEnfant = parametreService.findById(Parametre.class, "reduc_enfant");
            double tauxReductionEnfant = reductionEnfant != null ? reductionEnfant.getValeur() : 0.0;

            Double prixAdultes = volService.promotionAvailable(vol, typeSiegeId, nombreAdultes, prixInitial);

            // Prix enfants (avec réduction enfant)
            Double prixEnfants = volService.promotionAvailable(vol, typeSiegeId, nombreEnfants, prixInitial);
            prixEnfants = prixEnfants * (1 - tauxReductionEnfant / 100);

            Double prixTotal = prixAdultes + prixEnfants;

            // Création de la réservation
            reservationService.createReservation(placeVolId, utilisateur.getId(), typeSiegeId, nombrePlacesTotal,
                    prixTotal, nombreAdultes, nombreEnfants);
            modelAndView.setUrl("/ticket-vol/mes-reservations");
            return modelAndView;
        }
        modelAndView.setUrl("/ticket-vol/reserver?volId=" + volId + "&error=Vol inconnu");
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