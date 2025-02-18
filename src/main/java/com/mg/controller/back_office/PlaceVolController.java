package com.mg.controller.back_office;

import Annotation.*;
import Model.ModelAndView;
import com.mg.service.PlaceVolService;
import com.mg.service.VolService;
import com.mg.service.TypeSiegeService;
import com.mg.model.Vol;
import com.mg.model.PlaceVol;
import com.mg.model.TypeSiege;
import java.util.List;

@Controller
public class PlaceVolController {
    private final VolService volService;
    private final TypeSiegeService typeSiegeService;
    private final PlaceVolService placeVolService;

    public PlaceVolController() {
        this.volService = new VolService();
        this.typeSiegeService = new TypeSiegeService();
        this.placeVolService = new PlaceVolService();
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
        List<TypeSiege> typeSieges = typeSiegeService.findAll(TypeSiege.class);
        mv.add_data("vol", vol);
        mv.add_data("typeSieges", typeSieges);
        return mv;
    }

    @Post
    @Url(road_url = "/admin/vols/places/create")
    public ModelAndView createPlaceVol(
            @Param(name = "volId") Integer volId,
            @Param(name = "typeSiegeId") Integer typeSiegeId,
            @Param(name = "prix") Double prix) throws Exception {

        Vol vol = volService.findById(Vol.class, volId);
        TypeSiege typeSiege = typeSiegeService.findById(TypeSiege.class, typeSiegeId);

        PlaceVol placeVol = new PlaceVol();
        placeVol.setVol(vol);
        placeVol.setTypeSiege(typeSiege);
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
        List<TypeSiege> typeSieges = typeSiegeService.findAll(TypeSiege.class);

        mv.add_data("vol", vol);
        mv.add_data("placeVol", placeVol);
        mv.add_data("typeSieges", typeSieges);
        return mv;
    }

    @Post
    @Url(road_url = "/admin/vols/places/edit")
    public ModelAndView updatePlaceVol(
            @Param(name = "id") Integer id,
            @Param(name = "volId") Integer volId,
            @Param(name = "typeSiegeId") Integer typeSiegeId,
            @Param(name = "prix") Double prix) throws Exception {

        PlaceVol placeVol = placeVolService.findById(PlaceVol.class, id);
        TypeSiege typeSiege = typeSiegeService.findById(TypeSiege.class, typeSiegeId);

        placeVol.setTypeSiege(typeSiege);
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