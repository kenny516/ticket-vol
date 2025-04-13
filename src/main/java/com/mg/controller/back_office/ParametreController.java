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

        Double delaiReservation = parametreService.getValeurParametre("delai_reservation", 24.0);
        Double delaiAnnulation = parametreService.getValeurParametre("delai_annulation", 48.0);
        Double reductionEnfant = parametreService.getValeurParametre("reduc_enfant", 20.0);

        mv.add_data("delaiReservation", delaiReservation);
        mv.add_data("delaiAnnulation", delaiAnnulation);
        mv.add_data("reductionEnfant", reductionEnfant);

        return mv;
    }

    @Post
    @Url(road_url = "/admin/parametres/update")
    public ModelAndView updateParametres(
            @Param(name = "delaiReservation") Double delaiReservation,
            @Param(name = "delaiAnnulation") Double delaiAnnulation,
            @Param(name = "reductionEnfant") Double reductionEnfant) throws Exception {

        parametreService.updateDelais(delaiReservation, delaiAnnulation);
        parametreService.updateReductionEnfant(reductionEnfant);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setIsRedirect(true);
        modelAndView.setUrl("/ticket-vol/admin/parametres");
        return modelAndView;
    }
}