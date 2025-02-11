package com.mg.controller.back_office;

import Annotation.*;
import Model.ModelAndView;
import com.mg.dao.ParametreDAO;
import com.mg.model.Parametre;

@Controller
public class ParametreController {
    private final ParametreDAO parametreDAO = new ParametreDAO();

    @Get
    @Url(road_url = "/admin/parametres")
    public ModelAndView parametresForm() throws Exception {
        ModelAndView mv = new ModelAndView("/back-office/parametres/form.jsp");

        // Récupérer les paramètres actuels ou créer un nouveau si non existant
        Parametre parametre = parametreDAO.findFirst();
        if (parametre == null) {
            parametre = new Parametre();
            parametre.setHeuresMinimumReservation(24); // Valeur par défaut
            parametre.setHeuresMinimumAnnulation(48); // Valeur par défaut
            parametreDAO.save(parametre);
        }

        mv.add_data("parametre", parametre);
        return mv;
    }

    @Post
    @Url(road_url = "/admin/parametres/update")
    public ModelAndView updateParametres(
            @Param(name = "heuresMinimumReservation") Integer heuresMinimumReservation,
            @Param(name = "heuresMinimumAnnulation") Integer heuresMinimumAnnulation) throws Exception {

        Parametre parametre = parametreDAO.findFirst();
        if (parametre == null) {
            parametre = new Parametre();
        }

        parametre.setHeuresMinimumReservation(heuresMinimumReservation);
        parametre.setHeuresMinimumAnnulation(heuresMinimumAnnulation);

        if (parametre.getId() == null) {
            parametreDAO.save(parametre);
        } else {
            parametreDAO.update(parametre);
        }

        return new ModelAndView("redirect:/admin/parametres");
    }
}