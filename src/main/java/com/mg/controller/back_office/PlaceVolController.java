package com.mg.controller.back_office;

import Annotation.*;
import Model.ModelAndView;
import com.mg.service.PlaceVolService;
import com.mg.service.VolService;
import com.mg.service.TypeSiegeService;
import com.mg.service.PlaceService;
import com.mg.model.Vol;
import com.mg.model.PlaceVol;
import com.mg.model.TypeSiege;
import com.mg.model.Place;
import java.util.List;

@Controller
public class PlaceVolController {
    private final VolService volService;
    private final TypeSiegeService typeSiegeService;
    private final PlaceVolService placeVolService;
    private final PlaceService placeService;

    public PlaceVolController() {
        this.volService = new VolService();
        this.typeSiegeService = new TypeSiegeService();
        this.placeVolService = new PlaceVolService();
        this.placeService = new PlaceService();
    }

    @Get
    @Url(road_url = "/admin/vols/places")
    public ModelAndView listPlacesVol(@Param(name = "volId") Integer volId) throws Exception {
        ModelAndView mv = new ModelAndView("/back-office/vols/places/list.jsp");
        Vol vol = volService.findById(Vol.class, volId, "placeVols");
        mv.add_data("vol", vol);
        mv.add_data("placeVols", vol.getPlaceVols());
        return mv;
    }

    @Get
    @Url(road_url = "/admin/vols/places/create")
    public ModelAndView createForm(@Param(name = "volId") Integer volId) throws Exception {
        ModelAndView mv = new ModelAndView("/back-office/vols/places/form.jsp");
        Vol vol = volService.findById(Vol.class, volId);
        // Récupérer les places disponibles pour cet avion
        List<Place> places = placeService.findByAvionForVol(vol.getAvion().getId(),vol.getId());
        mv.add_data("vol", vol);
        mv.add_data("places", places);
        return mv;
    }

    @Post
    @Url(road_url = "/admin/vols/places/create")
    public ModelAndView createPlaceVol(
            @Param(name = "volId") Integer volId,
            @Param(name = "placeId") Integer placeId,
            @Param(name = "prix") Double prix) throws Exception {
        Vol vol = volService.findById(Vol.class, volId);
        Place place = placeService.findById(Place.class, placeId);

        PlaceVol placeVol = new PlaceVol();
        placeVol.setVol(vol);
        placeVol.setPlace(place);
        placeVol.setPrix(prix);

        placeVolService.save(placeVol);

        ModelAndView mv = new ModelAndView("/ticket-vol/admin/vols/places?volId=" + volId);
        mv.setIsRedirect(true);
        return mv;
    }

    @Get
    @Url(road_url = "/admin/vols/places/edit")
    public ModelAndView editForm(
            @Param(name = "id") Integer id,
            @Param(name = "volId") Integer volId) throws Exception {
        ModelAndView mv = new ModelAndView("/back-office/vols/places/form.jsp");
        Vol vol = volService.findById(Vol.class, volId);
        PlaceVol placeVol = placeVolService.findById(PlaceVol.class, id);
        // Récupérer les places disponibles pour cet avion
        List<Place> places = placeService.findByAvion(vol.getAvion().getId());

        mv.add_data("vol", vol);
        mv.add_data("placeVol", placeVol);
        mv.add_data("places", places);
        return mv;
    }

    @Post
    @Url(road_url = "/admin/vols/places/edit")
    public ModelAndView updatePlaceVol(
            @Param(name = "id") Integer id,
            @Param(name = "volId") Integer volId,
            @Param(name = "placeId") Integer placeId,
            @Param(name = "prix") Double prix) throws Exception {
        PlaceVol placeVol = placeVolService.findById(PlaceVol.class, id);
        Place place = placeService.findById(Place.class, placeId);

        placeVol.setPlace(place);
        placeVol.setPrix(prix);

        placeVolService.update(placeVol);

        ModelAndView mv = new ModelAndView("/ticket-vol/admin/vols/places?volId=" + volId);
        mv.setIsRedirect(true);
        return mv;
    }

    @Post
    @Url(road_url = "/admin/vols/places/delete")
    public ModelAndView deletePlaceVol(
            @Param(name = "id") Integer id,
            @Param(name = "volId") Integer volId) throws Exception {
        PlaceVol placeVol = placeVolService.findById(PlaceVol.class, id);
        if (placeVol != null) {
            placeVolService.delete(placeVol);
        }
        ModelAndView mv = new ModelAndView("/ticket-vol/admin/vols/places?volId=" + volId);
        mv.setIsRedirect(true);
        return mv;
    }
}