package com.mg.controller;

import Annotation.*;
import Model.CustomSession;
import Model.ModelAndView;
import com.mg.service.UtilisateurService;
import com.mg.model.Utilisateur;

@Controller
public class AuthController {
    private final UtilisateurService utilisateurService;

    public AuthController() {
        this.utilisateurService = new UtilisateurService();
    }

    @Get
    @Url(road_url = "/loginForm")
    public ModelAndView loginForm() throws Exception {
        return new ModelAndView("/front-office/login.jsp");
    }

    @Post
    @Url(road_url = "/login")
    public ModelAndView login(
            @Param(name = "pseudo") String pseudo,
            @Param(name = "motDePasse") String motDePasse,
            CustomSession customSession) throws Exception {

        Utilisateur utilisateur = utilisateurService.login(pseudo, motDePasse);
        ModelAndView modelAndView = new ModelAndView();
        if (utilisateur != null) {
            customSession.addSession("user", utilisateur);
            customSession.addSession("role", utilisateur.getRole());
            if ("admin".equals(utilisateur.getRole())) {
                modelAndView.setUrl("/back-office/index.jsp");
            } else {
                modelAndView.setIsRedirect(true);
                modelAndView.setUrl("/ticket-vol/vols/search");
            }
        }else {
            modelAndView.setUrl("/front-office/login.jsp");
            modelAndView.add_data("error", "Nom d'utilisateur ou mot de passe incorrect");
        }

        return modelAndView;
    }


    @Get
    @Url(road_url = "/logout")
    public ModelAndView logout(CustomSession session) throws Exception {
        session.destroySession();
        return new ModelAndView("/login");
    }
}