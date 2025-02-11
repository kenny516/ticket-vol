package com.mg.controller.front_office;

import Annotation.*;
import Model.ModelAndView;
import com.mg.dao.VilleDAO;
import com.mg.dao.VolDAO;
import com.mg.model.Ville;
import com.mg.model.Vol;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class VolSearchController {
    private final VolDAO volDAO = new VolDAO();
    private final VilleDAO villeDAO = new VilleDAO();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Get
    @Url(road_url = "/vols/search")
    public ModelAndView searchForm() throws Exception {
        ModelAndView mv = new ModelAndView("/front-office/vols/search.jsp");
        List<Ville> villes = villeDAO.findAll(Ville.class);
        mv.add_data("villes", villes);
        return mv;
    }

    @Post
    @Url(road_url = "/vols/search")
    public ModelAndView searchVols(
            @Param(name = "villeDepart") Integer villeDepartId,
            @Param(name = "villeArrive") Integer villeArriveId,
            @Param(name = "dateDepart") String dateDepartStr,
            @Param(name = "maxPrice") Double maxPrice) throws Exception {

        ModelAndView mv = new ModelAndView("/front-office/vols/results.jsp");

        Ville villeDepart = villeDepartId != null ? villeDAO.findById(Ville.class, villeDepartId) : null;
        Ville villeArrive = villeArriveId != null ? villeDAO.findById(Ville.class, villeArriveId) : null;
        Date dateDepart = dateDepartStr != null && !dateDepartStr.isEmpty() ? dateFormat.parse(dateDepartStr) : null;

        List<Vol> vols = volDAO.searchVols(villeDepart, villeArrive, dateDepart, maxPrice);
        mv.add_data("vols", vols);
        mv.add_data("dateDepart", dateDepartStr);

        // Garder les critères de recherche pour le formulaire
        List<Ville> villes = villeDAO.findAll(Ville.class);
        mv.add_data("villes", villes);
        mv.add_data("selectedVilleDepartId", villeDepartId);
        mv.add_data("selectedVilleArriveId", villeArriveId);
        mv.add_data("maxPrice", maxPrice);

        return mv;
    }
}