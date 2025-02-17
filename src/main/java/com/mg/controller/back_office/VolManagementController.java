package com.mg.controller.back_office;

import Annotation.*;
import Model.ModelAndView;
import com.mg.service.VolService;
import com.mg.service.VilleService;
import com.mg.service.AvionService;
import com.mg.model.Vol;
import com.mg.model.Ville;
import com.mg.model.Avion;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

@Controller
public class VolManagementController {
    private final VolService volService;
    private final VilleService villeService;
    private final AvionService avionService;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

    public VolManagementController() {
        this.volService = new VolService();
        this.villeService = new VilleService();
        this.avionService = new AvionService();
    }

    @Get
    @Url(road_url = "/admin/vols")
    public ModelAndView listVols(
            @Param(name = "villeDepartId") Integer villeDepartId,
            @Param(name = "villeArriveId") Integer villeArriveId,
            @Param(name = "dateDebut") String dateDebut,
            @Param(name = "dateFin") String dateFin,
            @Param(name = "prixMin") Double prixMin,
            @Param(name = "prixMax") Double prixMax) throws Exception {

        ModelAndView mv = new ModelAndView("/back-office/vols/list.jsp");

        List<Ville> villes = villeService.findAll(Ville.class);
        mv.add_data("villes", villes);

        Date dateDebutObj = dateDebut != null ? dateFormat.parse(dateDebut) : null;
        Date dateFinObj = dateFin != null ? dateFormat.parse(dateFin) : null;

        List<Vol> vols;
        if (villeDepartId != null || villeArriveId != null || dateDebut != null ||
                dateFin != null || prixMin != null || prixMax != null) {
            Ville villeDepart = villeDepartId != null ? villeService.findById(Ville.class, villeDepartId) : null;
            Ville villeArrive = villeArriveId != null ? villeService.findById(Ville.class, villeArriveId) : null;
            vols = volService.searchVolsAdvanced(villeDepart, villeArrive, dateDebutObj, dateFinObj, prixMin, prixMax);
        } else {
            vols = volService.findAll(Vol.class,"placeVols");
        }

        mv.add_data("vols", vols);
        mv.add_data("villeDepartId", villeDepartId);
        mv.add_data("villeArriveId", villeArriveId);
        mv.add_data("dateDebut", dateDebut);
        mv.add_data("dateFin", dateFin);
        mv.add_data("prixMin", prixMin);
        mv.add_data("prixMax", prixMax);

        return mv;
    }

    @Get
    @Url(road_url = "/admin/vols/create")
    public ModelAndView createForm() throws Exception {
        ModelAndView mv = new ModelAndView("/back-office/vols/form.jsp");
        List<Ville> villes = villeService.findAll(Ville.class);
        List<Avion> avions = avionService.findAll(Avion.class);
        mv.add_data("villes", villes);
        mv.add_data("avions", avions);
        return mv;
    }

    @Post
    @Url(road_url = "/admin/vols/create")
    public ModelAndView createVol(
            @Param(name = "villeDepartId") Integer villeDepartId,
            @Param(name = "villeArriveId") Integer villeArriveId,
            @Param(name = "avionId") Integer avionId,
            @Param(name = "dateDepart") String dateDepart) throws Exception {

        volService.createVol(villeDepartId, villeArriveId, avionId, dateFormat.parse(dateDepart));
        ModelAndView mv = new ModelAndView("/ticket-vol/admin/vols");
        mv.setIsRedirect(true);
        return mv;
    }

    @Get
    @Url(road_url = "/admin/vols/edit")
    public ModelAndView editForm(@Param(name = "id") Integer id) throws Exception {
        ModelAndView mv = new ModelAndView("/back-office/vols/form.jsp");
        Vol vol = volService.findById(Vol.class, id);
        List<Ville> villes = villeService.findAll(Ville.class);
        List<Avion> avions = avionService.findAll(Avion.class);

        mv.add_data("vol", vol);
        mv.add_data("villes", villes);
        mv.add_data("avions", avions);
        return mv;
    }

    @Post
    @Url(road_url = "/admin/vols/edit")
    public ModelAndView updateVol(
            @Param(name = "id") Integer id,
            @Param(name = "villeDepartId") Integer villeDepartId,
            @Param(name = "villeArriveId") Integer villeArriveId,
            @Param(name = "avionId") Integer avionId,
            @Param(name = "dateDepart") String dateDepart) throws Exception {

        volService.updateVol(id, villeDepartId, villeArriveId, avionId, dateFormat.parse(dateDepart));
        ModelAndView mv = new ModelAndView("/ticket-vol/admin/vols");
        mv.setIsRedirect(true);
        return mv;
    }

    @Post
    @Url(road_url = "/admin/vols/delete")
    public ModelAndView deleteVol(@Param(name = "id") Integer id) throws Exception {
        Vol vol = volService.findById(Vol.class, id);
        if (vol != null) {
            volService.delete(vol);
        }
        ModelAndView mv = new ModelAndView("/ticket-vol/admin/vols");
        mv.setIsRedirect(true);
        return mv;
    }
}