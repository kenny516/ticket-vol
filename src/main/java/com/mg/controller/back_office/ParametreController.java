package com.mg.controller.back_office;

import Annotation.*;
import Model.ModelAndView;
import com.mg.service.ParametreService;
import com.mg.model.Parametre;

@Controller
public class ParametreController {
    private final ParametreService parametreService;

    public ParametreController() {
        this.parametreService = new ParametreService();
    }

    @Get
    @Url(road_url = "/admin/parametres")
    public ModelAndView parametresForm() throws Exception {
        ModelAndView mv = new ModelAndView("/back-office/parametres/form.jsp");
        Parametre parametre = parametreService.getParametres();
        mv.add_data("parametre", parametre);
        return mv;
    }

    @Post
    @Url(road_url = "/admin/parametres/update")
    public ModelAndView updateParametres(
            @Param(name = "heuresMinimumReservation") Integer heuresMinimumReservation,
            @Param(name = "heuresMinimumAnnulation") Integer heuresMinimumAnnulation) throws Exception {

        parametreService.updateParametres(heuresMinimumReservation, heuresMinimumAnnulation);
        return new ModelAndView("redirect:/admin/parametres");
    }
}