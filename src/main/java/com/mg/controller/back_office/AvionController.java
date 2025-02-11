package com.mg.controller.back_office;

import Annotation.*;
import Model.ModelAndView;
import com.mg.dao.AvionDAO;
import com.mg.dao.PlaceDAO;
import com.mg.dao.TypeSiegeDAO;
import com.mg.model.Avion;
import com.mg.model.Place;
import com.mg.model.TypeSiege;

import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class AvionController {
    private final AvionDAO avionDAO = new AvionDAO();
    private final TypeSiegeDAO typeSiegeDAO = new TypeSiegeDAO();
    private final PlaceDAO placeDAO = new PlaceDAO();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Get
    @Url(road_url = "/admin/avions")
    public ModelAndView listAvions() throws Exception {
        ModelAndView mv = new ModelAndView("/back-office/avions/list.jsp");
        List<Avion> avions = avionDAO.findAll(Avion.class);
        mv.add_data("avions", avions);
        return mv;
    }

    @Get
    @Url(road_url = "/admin/avions/create")
    public ModelAndView createForm() throws Exception {
        ModelAndView mv = new ModelAndView("/back-office/avions/form.jsp");
        List<TypeSiege> typeSieges = typeSiegeDAO.findAll(TypeSiege.class);
        mv.add_data("typeSieges", typeSieges);
        return mv;
    }

    @Post
    @Url(road_url = "/admin/avions/create")
    public ModelAndView createAvion(
            @Param(name = "modele") String modele,
            @Param(name = "dateFabrication") String dateFabrication,
            @Param(name = "typeSieges") Long[] typeSiegeIds,
            @Param(name = "nombrePlaces") Integer[] nombrePlaces) throws Exception {

        Avion avion = new Avion();
        avion.setModele(modele);
        avion.setDateFabrication(dateFormat.parse(dateFabrication));

        avionDAO.save(avion);

        // Création des places pour chaque type de siège
        for (int i = 0; i < typeSiegeIds.length; i++) {
            if (typeSiegeIds[i] != null && nombrePlaces[i] != null && nombrePlaces[i] > 0) {
                Place place = new Place();
                place.setAvion(avion);
                place.setTypeSiege(typeSiegeDAO.findById(TypeSiege.class, typeSiegeIds[i]));
                place.setNombre(nombrePlaces[i]);
                placeDAO.save(place);
            }
        }

        return new ModelAndView("redirect:/admin/avions");
    }

    @Get
    @Url(road_url = "/admin/avions/edit")
    public ModelAndView editForm(@Param(name = "id") Long id) throws Exception {
        ModelAndView mv = new ModelAndView("/back-office/avions/form.jsp");
        Avion avion = avionDAO.findById(Avion.class, id);
        List<TypeSiege> typeSieges = typeSiegeDAO.findAll(TypeSiege.class);
        List<Place> places = placeDAO.findByAvion(id);

        mv.add_data("avion", avion);
        mv.add_data("typeSieges", typeSieges);
        mv.add_data("places", places);
        return mv;
    }

    @Post
    @Url(road_url = "/admin/avions/edit")
    public ModelAndView updateAvion(
            @Param(name = "id") Long id,
            @Param(name = "modele") String modele,
            @Param(name = "dateFabrication") String dateFabrication,
            @Param(name = "typeSieges") Long[] typeSiegeIds,
            @Param(name = "nombrePlaces") Integer[] nombrePlaces) throws Exception {

        Avion avion = avionDAO.findById(Avion.class, id);
        avion.setModele(modele);
        avion.setDateFabrication(dateFormat.parse(dateFabrication));

        avionDAO.update(avion);

        // Mettre à jour les places existantes
        List<Place> existingPlaces = placeDAO.findByAvion(id);
        for (Place place : existingPlaces) {
            placeDAO.delete(place);
        }

        // Créer les nouvelles places
        for (int i = 0; i < typeSiegeIds.length; i++) {
            if (typeSiegeIds[i] != null && nombrePlaces[i] != null && nombrePlaces[i] > 0) {
                Place place = new Place();
                place.setAvion(avion);
                place.setTypeSiege(typeSiegeDAO.findById(TypeSiege.class, typeSiegeIds[i]));
                place.setNombre(nombrePlaces[i]);
                placeDAO.save(place);
            }
        }

        return new ModelAndView("redirect:/admin/avions");
    }

    @Post
    @Url(road_url = "/admin/avions/delete")
    public ModelAndView deleteAvion(@Param(name = "id") Long id) throws Exception {
        Avion avion = avionDAO.findById(Avion.class, id);
        if (avion != null) {
            // Supprimer d'abord les places associées
            List<Place> places = placeDAO.findByAvion(id);
            for (Place place : places) {
                placeDAO.delete(place);
            }
            avionDAO.delete(avion);
        }
        return new ModelAndView("redirect:/admin/avions");
    }
}