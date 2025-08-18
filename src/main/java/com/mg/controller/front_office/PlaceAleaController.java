package com.mg.controller.front_office;

import Annotation.*;
import Annotation.auth.Auth;
import Model.ModelAndView;
import com.mg.model.PlaceAlea;
import com.mg.model.Vol;
import com.mg.model.TypeSiege;
import com.mg.service.PlaceAleaService;
import com.mg.service.VolService;
import com.mg.service.TypeSiegeService;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Controller
@Auth(roles = "client")
public class PlaceAleaController {
    private final PlaceAleaService placeAleaService;
    private final VolService volService;
    private final TypeSiegeService typeSiegeService;
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    public PlaceAleaController() {
        this.placeAleaService = new PlaceAleaService();
        this.volService = new VolService();
        this.typeSiegeService = new TypeSiegeService();
    }

    @Get
    @Url(road_url = "/places-alea")
    public ModelAndView listPlacesAlea() throws Exception {
        ModelAndView mv = new ModelAndView("/front-office/places-alea/list.jsp");

        // Récupérer toutes les places aléatoires
        List<PlaceAlea> placesAlea = placeAleaService.findAll();

        // Récupérer les données pour les filtres
        List<Vol> vols = volService.findAll(Vol.class);
        List<TypeSiege> typeSieges = typeSiegeService.findAll(TypeSiege.class);

        mv.add_data("placesAlea", placesAlea);
        mv.add_data("vols", vols);
        mv.add_data("typeSieges", typeSieges);

        return mv;
    }

    @Post
    @Url(road_url = "/places-alea/search")
    public ModelAndView searchPlacesAlea(
            @Param(name = "volId") Integer volId,
            @Param(name = "typeSiegeId") Integer typeSiegeId,
            @Param(name = "dateDebut") String dateDebutStr,
            @Param(name = "dateFin") String dateFinStr,
            @Param(name = "prixMin") Double prixMin,
            @Param(name = "prixMax") Double prixMax) throws Exception {

        ModelAndView mv = new ModelAndView("/front-office/places-alea/list.jsp");

        // Conversion des dates
        Date dateDebut = null;
        Date dateFin = null;
        try {
            dateDebut = dateDebutStr != null && !dateDebutStr.isEmpty() ? dateFormatter.parse(dateDebutStr) : null;
            dateFin = dateFinStr != null && !dateFinStr.isEmpty() ? dateFormatter.parse(dateFinStr) : null;
        } catch (Exception e) {
            // En cas d'erreur de parsing, on ignore les dates
        }

        // Recherche avec filtres
        List<PlaceAlea> placesAlea = placeAleaService.searchPlacesAlea(
                volId, typeSiegeId, dateDebut, dateFin, prixMin, prixMax);

        // Récupérer les données pour les filtres
        List<Vol> vols = volService.findAll(Vol.class);
        List<TypeSiege> typeSieges = typeSiegeService.findAll(TypeSiege.class);

        // Ajouter les données à la vue
        mv.add_data("placesAlea", placesAlea);
        mv.add_data("vols", vols);
        mv.add_data("typeSieges", typeSieges);

        // Garder les critères de recherche pour le formulaire
        mv.add_data("volId", volId);
        mv.add_data("typeSiegeId", typeSiegeId);
        mv.add_data("dateDebut", dateDebutStr);
        mv.add_data("dateFin", dateFinStr);
        mv.add_data("prixMin", prixMin);
        mv.add_data("prixMax", prixMax);

        return mv;
    }
}
