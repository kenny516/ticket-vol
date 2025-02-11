package com.mg.controller.back_office;

import Annotation.*;
import Model.ModelAndView;
import com.mg.dao.PromotionDAO;
import com.mg.dao.VolDAO;
import com.mg.dao.TypeSiegeDAO;
import com.mg.model.Promotion;
import com.mg.model.Vol;
import com.mg.model.TypeSiege;

import java.util.List;

@Controller
public class PromotionController {
    private final PromotionDAO promotionDAO = new PromotionDAO();
    private final VolDAO volDAO = new VolDAO();
    private final TypeSiegeDAO typeSiegeDAO = new TypeSiegeDAO();

    @Get
    @Url(road_url = "/admin/promotions")
    public ModelAndView listPromotions(@Param(name = "volId") Integer volId) throws Exception {
        ModelAndView mv = new ModelAndView("/back-office/promotions/list.jsp");

        List<Vol> vols = volDAO.findAll(Vol.class);
        mv.add_data("vols", vols);

        if (volId != null) {
            Vol selectedVol = volDAO.findById(Vol.class, volId);
            List<Promotion> promotions = promotionDAO.findByVol(volId);
            mv.add_data("selectedVol", selectedVol);
            mv.add_data("promotions", promotions);
        } else {
            List<Promotion> promotions = promotionDAO.findAll(Promotion.class);
            mv.add_data("promotions", promotions);
        }

        return mv;
    }

    @Get
    @Url(road_url = "/admin/promotions/create")
    public ModelAndView createForm(@Param(name = "volId") Integer volId) throws Exception {
        ModelAndView mv = new ModelAndView("/back-office/promotions/form.jsp");

        List<Vol> vols = volDAO.findAll(Vol.class);
        List<TypeSiege> typeSieges = typeSiegeDAO.findAll(TypeSiege.class);

        mv.add_data("vols", vols);
        mv.add_data("typeSieges", typeSieges);

        if (volId != null) {
            Vol selectedVol = volDAO.findById(Vol.class, volId);
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

        Promotion promotion = new Promotion();
        promotion.setVol(volDAO.findById(Vol.class, volId));
        promotion.setTypeSiege(typeSiegeDAO.findById(TypeSiege.class, typeSiegeId));
        promotion.setNbSiege(nbSiege);
        promotion.setPourcentageReduction(reduction);

        promotionDAO.save(promotion);

        return new ModelAndView("redirect:/admin/promotions?volId=" + volId);
    }

    @Post
    @Url(road_url = "/admin/promotions/delete")
    public ModelAndView deletePromotion(
            @Param(name = "id") Integer id,
            @Param(name = "volId") Long volId) throws Exception {
        
        Promotion promotion = promotionDAO.findById(Promotion.class, id);
        if (promotion != null) {
            promotionDAO.delete(promotion);        }

        return new ModelAndView("redirect:/admin/promotions" + (volId != null ? "?volId=" + volId : ""));
    }
}