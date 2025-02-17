package com.mg.controller.front_office;

import Annotation.*;
import Annotation.auth.Auth;
import Model.ModelAndView;
import com.mg.DTO.VolDTO;
import com.mg.dao.VilleDAO;
import com.mg.dao.VolDAO;
import com.mg.model.Ville;
import com.mg.model.Vol;
import com.mg.service.VilleService;
import com.mg.service.VolService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@Auth(roles = "client")
public class VolSearchController {
    private final VolService volService = new VolService();
    private final VilleService villeService = new VilleService();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Get
    @Url(road_url = "/vols/search")
    public ModelAndView searchForm() throws Exception {
        ModelAndView mv = new ModelAndView("/front-office/vols/list.jsp");
        List<Ville> villes = villeService.findAll(Ville.class);
        List<Vol> vols = volService.getVolValide();
        mv.add_data("vols", vols);
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

        ModelAndView mv = new ModelAndView("/front-office/vols/list.jsp");

        Ville villeDepart = villeDepartId != null ? villeService.findById(villeDepartId) : null;
        Ville villeArrive = villeArriveId != null ? villeService.findById(villeArriveId) : null;
        Date dateDepart = dateDepartStr != null && !dateDepartStr.isEmpty() ? dateFormat.parse(dateDepartStr) : null;

        List<Vol> vols = volService.searchVols(villeDepart, villeArrive, dateDepart, maxPrice);
        mv.add_data("vols", vols);
        mv.add_data("dateDepart", dateDepartStr);

        // Garder les critères de recherche pour le formulaire
        List<Ville> villes = villeService.findAll(Ville.class);
        mv.add_data("villes", villes);
        mv.add_data("villeDepartId", villeDepartId);
        mv.add_data("villeArriveId", villeArriveId);
        mv.add_data("maxPrice", maxPrice);

        return mv;
    }
}