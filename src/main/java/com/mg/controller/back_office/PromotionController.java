package com.mg.controller.back_office;

import Annotation.*;
import Model.ModelAndView;
import com.mg.service.PromotionService;
import com.mg.service.VolService;
import com.mg.service.TypeSiegeService;
import com.mg.model.Promotion;
import com.mg.model.Vol;
import com.mg.model.TypeSiege;
import java.util.List;

@Controller
public class PromotionController {
    private final PromotionService promotionService;
    private final VolService volService;
    private final TypeSiegeService typeSiegeService;

    public PromotionController() {
        this.promotionService = new PromotionService();
        this.volService = new VolService();
        this.typeSiegeService = new TypeSiegeService();
    }

    @Get
    @Url(road_url = "/admin/promotions")
    public ModelAndView listPromotions(@Param(name = "volId") Integer volId) throws Exception {
        ModelAndView mv = new ModelAndView("/back-office/promotions/list.jsp");

        List<Vol> vols = volService.findAll(Vol.class);
        mv.add_data("vols", vols);

        if (volId != null) {
            Vol selectedVol = volService.findById(Vol.class, volId);
            List<Promotion> promotions = promotionService.findByVol(volId);
            mv.add_data("selectedVol", selectedVol);
            mv.add_data("promotions", promotions);
        } else {
            List<Promotion> promotions = promotionService.findAll(Promotion.class);
            mv.add_data("promotions", promotions);
        }

        return mv;
    }

    @Get
    @Url(road_url = "/admin/promotions/create")
    public ModelAndView createForm(@Param(name = "volId") Integer volId) throws Exception {
        ModelAndView mv = new ModelAndView("/back-office/promotions/form.jsp");

        List<Vol> vols = volService.findAll(Vol.class);
        List<TypeSiege> typeSieges = typeSiegeService.findAll(TypeSiege.class);

        mv.add_data("vols", vols);
        mv.add_data("typeSieges", typeSieges);

        if (volId != null) {
            Vol selectedVol = volService.findById(Vol.class, volId);
            mv.add_data("selectedVol", selectedVol);
        }

        return mv;
    }

    @Post
    @Url(road_url = "/admin/promotions/create")
    public ModelAndView createPromotion(
            @Param(name = "volId") Integer volId,
            @Param(name = "typeSiegeId") Integer typeSiegeId,
            @Param(name = "nbSiege") Integer nbSiege,
            @Param(name = "reduction") Double reduction) throws Exception {

        promotionService.createPromotion(volId, typeSiegeId, nbSiege, reduction);
        ModelAndView modelAndView = new ModelAndView("/ticket-vol/admin/promotions?volId=" + volId);
        modelAndView.setIsRedirect(true);
        return modelAndView;
    }

    @Post
    @Url(road_url = "/admin/promotions/delete")
    public ModelAndView deletePromotion(
            @Param(name = "id") Integer id,
            @Param(name = "volId") Long volId) throws Exception {

        Promotion promotion = promotionService.findById(Promotion.class, id);
        if (promotion != null) {
            promotionService.delete(promotion);
        }
        ModelAndView modelAndView = new ModelAndView("/admin/promotions" + (volId != null ? "?volId=" + volId : ""));
        modelAndView.setIsRedirect(true);
        return modelAndView;
    }
}