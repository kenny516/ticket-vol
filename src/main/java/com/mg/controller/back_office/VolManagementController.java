package com.mg.controller.back_office;

import Annotation.*;
import Model.ModelAndView;
import com.mg.dao.VilleDAO;
import com.mg.dao.VolDAO;
import com.mg.dao.AvionDAO;
import com.mg.model.Ville;
import com.mg.model.Vol;
import com.mg.model.Avion;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class VolManagementController {
    private final VolDAO volDAO = new VolDAO();
    private final VilleDAO villeDAO = new VilleDAO();
    private final AvionDAO avionDAO = new AvionDAO();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

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

        // Charger les données pour les filtres
        List<Ville> villes = villeDAO.findAll(Ville.class);
        mv.add_data("villes", villes);

        // Appliquer les filtres
        List<Vol> vols;
        if (villeDepartId != null || villeArriveId != null || dateDebut != null ||
                dateFin != null || prixMin != null || prixMax != null) {

            Date debut = dateDebut != null ? dateFormat.parse(dateDebut) : null;
            Date fin = dateFin != null ? dateFormat.parse(dateFin) : null;

            vols = volDAO.searchVols(villeDepartId, villeArriveId, debut, fin, prixMin, prixMax);
        } else {
            vols = volDAO.findAll(Vol.class);
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
        List<Ville> villes = villeDAO.findAll(Ville.class);
        List<Avion> avions = avionDAO.findAll(Avion.class);
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
            @Param(name = "dateDepart") String dateDepart,
            @Param(name = "prix") Double prix) throws Exception {

        Vol vol = new Vol();
        vol.setVilleDepart(villeDAO.findById(Ville.class, villeDepartId));
        vol.setVilleArrive(villeDAO.findById(Ville.class, villeArriveId));
        vol.setAvion(avionDAO.findById(Avion.class, avionId));
        vol.setDateDepart(dateFormat.parse(dateDepart));
        vol.setPrix(prix);

        volDAO.save(vol);

        return new ModelAndView("redirect:/admin/vols");
    }

    @Get
    @Url(road_url = "/admin/vols/edit")
    public ModelAndView editForm(@Param(name = "id") Integer id) throws Exception {
        ModelAndView mv = new ModelAndView("/back-office/vols/form.jsp");
        Vol vol = volDAO.findById(Vol.class, id);
        List<Ville> villes = villeDAO.findAll(Ville.class);
        List<Avion> avions = avionDAO.findAll(Avion.class);

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
            @Param(name = "dateDepart") String dateDepart,
            @Param(name = "prix") Double prix) throws Exception {

        Vol vol = volDAO.findById(Vol.class, id);
        vol.setVilleDepart(villeDAO.findById(Ville.class, villeDepartId));
        vol.setVilleArrive(villeDAO.findById(Ville.class, villeArriveId));
        vol.setAvion(avionDAO.findById(Avion.class, avionId));
        vol.setDateDepart(dateFormat.parse(dateDepart));
        vol.setPrix(prix);

        volDAO.update(vol);

        return new ModelAndView("redirect:/admin/vols");
    }

    @Post
    @Url(road_url = "/admin/vols/delete")
    public ModelAndView deleteVol(@Param(name = "id") Integer id) throws Exception {
        Vol vol = volDAO.findById(Vol.class, id);
        if (vol != null) {
            volDAO.delete(vol);
        }
        return new ModelAndView("redirect:/admin/vols");
    }

    @Get
    @Url(road_url = "/admin/vols/search")
    public ModelAndView searchVols(
            @Param(name = "villeDepartId") Integer villeDepartId,
            @Param(name = "villeArriveId") Integer villeArriveId,
            @Param(name = "dateDebut") String dateDebut,
            @Param(name = "dateFin") String dateFin,
            @Param(name = "prixMin") Double prixMin,
            @Param(name = "prixMax") Double prixMax) throws Exception {

        ModelAndView mv = new ModelAndView("/back-office/vols/list.jsp");

        Date dateDebutObj = dateDebut != null ? dateFormat.parse(dateDebut) : null;
        Date dateFinObj = dateFin != null ? dateFormat.parse(dateFin) : null;

        List<Vol> vols = volDAO.searchVolsAdvanced(
                villeDepartId != null ? villeDAO.findById(Ville.class, villeDepartId) : null,
                villeArriveId != null ? villeDAO.findById(Ville.class, villeArriveId) : null,
                dateDebutObj,
                dateFinObj,
                prixMin,
                prixMax);

        List<Ville> villes = villeDAO.findAll(Ville.class);

        mv.add_data("vols", vols);
        mv.add_data("villes", villes);
        mv.add_data("villeDepartId", villeDepartId);
        mv.add_data("villeArriveId", villeArriveId);
        mv.add_data("dateDebut", dateDebut);
        mv.add_data("dateFin", dateFin);
        mv.add_data("prixMin", prixMin);
        mv.add_data("prixMax", prixMax);

        return mv;
    }
}