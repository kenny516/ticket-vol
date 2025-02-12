package com.mg.controller.back_office;

import Annotation.*;
import Model.ModelAndView;
import com.mg.service.AvionService;
import com.mg.service.TypeSiegeService;
import com.mg.model.Avion;
import com.mg.model.Place;
import com.mg.model.TypeSiege;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class AvionController {
    private final AvionService avionService;
    private final TypeSiegeService typeSiegeService;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public AvionController() {
        this.avionService = new AvionService();
        this.typeSiegeService = new TypeSiegeService();
    }

    @Get
    @Url(road_url = "/admin/avions")
    public ModelAndView listAvions() throws Exception {
        ModelAndView mv = new ModelAndView("/back-office/avions/list.jsp");
        List<Avion> avions = avionService.findAll(Avion.class);
        mv.add_data("avions", avions);
        return mv;
    }

    @Get
    @Url(road_url = "/admin/avions/create")
    public ModelAndView createForm() throws Exception {
        ModelAndView mv = new ModelAndView("/back-office/avions/form.jsp");
        List<TypeSiege> typeSieges = typeSiegeService.findAll(TypeSiege.class);
        mv.add_data("typeSieges", typeSieges);
        return mv;
    }

    @Post
    @Url(road_url = "/admin/avions/create")
    public ModelAndView createAvion(
            @Param(name = "modele") String modele,
            @Param(name = "dateFabrication") String dateFabrication,
            @Param(name = "typeSieges") Integer[] typeSiegeIds,
            @Param(name = "nombrePlaces") Integer[] nombrePlaces) throws Exception {

        avionService.createAvion(modele, dateFormat.parse(dateFabrication), typeSiegeIds, nombrePlaces);
        return new ModelAndView("redirect:/admin/avions");
    }

    @Get
    @Url(road_url = "/admin/avions/edit")
    public ModelAndView editForm(@Param(name = "id") Integer id) throws Exception {
        ModelAndView mv = new ModelAndView("/back-office/avions/form.jsp");
        Avion avion = avionService.findById(Avion.class, id);
        List<TypeSiege> typeSieges = typeSiegeService.findAll(TypeSiege.class);
        List<Place> places = avion.getPlaces();

        mv.add_data("avion", avion);
        mv.add_data("typeSieges", typeSieges);
        mv.add_data("places", places);
        return mv;
    }

    @Post
    @Url(road_url = "/admin/avions/edit")
    public ModelAndView updateAvion(
            @Param(name = "id") Integer id,
            @Param(name = "modele") String modele,
            @Param(name = "dateFabrication") String dateFabrication,
            @Param(name = "typeSieges") Integer[] typeSiegeIds,
            @Param(name = "nombrePlaces") Integer[] nombrePlaces) throws Exception {

        avionService.updateAvion(id, modele, dateFormat.parse(dateFabrication), typeSiegeIds, nombrePlaces);
        return new ModelAndView("redirect:/admin/avions");
    }

    @Post
    @Url(road_url = "/admin/avions/delete")
    public ModelAndView deleteAvion(@Param(name = "id") Integer id) throws Exception {
        avionService.deleteAvion(id);
        return new ModelAndView("redirect:/admin/avions");
    }
}