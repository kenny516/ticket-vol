package com.mg.controller;

import Annotation.*;
import Model.CustomSession;
import Model.ModelAndView;
import com.mg.model.Utilisateur;
import com.mg.dao.UtilisateurDAO;

@Controller
public class LoginController {
    private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    @Get
    @Url(road_url = "/login")
    public ModelAndView loginForm(CustomSession customSession) {
        ModelAndView modelAndView = new ModelAndView("/front-office/login.jsp");
        if (customSession != null && customSession.getAttribute("user") != null) {
            Utilisateur user = (Utilisateur) customSession.getAttribute("user");
            if ("admin".equals(user.getRole())) {
                modelAndView.setUrl("/back-office/");
            } else {
                modelAndView.setUrl("/front-office/vols/list");
            }
        }
        return modelAndView;
    }

    @Post
    @Url(road_url = "/login")
    public ModelAndView loginVerif(@Param(name = "username") String username, @Param(name = "password") String password, CustomSession customSession) throws Exception {
        Utilisateur user = utilisateurDAO.findByUsername(username);
        ModelAndView modelAndView = new ModelAndView();
        if (user != null && password.equals(user.getMotDePasse())) {
            customSession.addSession("user", user);
            customSession.addSession("role", user.getRole());
            if ("admin".equals(user.getRole())) {
                modelAndView.setUrl("/back-office/index.jsp");
            } else {
                modelAndView.setIsRedirect(true);
                modelAndView.setUrl("/ticket-vol/vols/search");
            }
        } else {
            modelAndView.setUrl("/front-office/login.jsp");
            modelAndView.add_data("error", "Nom d'utilisateur ou mot de passe incorrect");
        }
        return modelAndView;
    }
}