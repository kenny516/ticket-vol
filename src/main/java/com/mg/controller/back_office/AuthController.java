package com.mg.controller.back_office;

import Annotation.*;
import Model.CustomSession;
import Model.ModelAndView;

@Controller
public class AuthController {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123"; // Dans un environnement de production, utiliser un hash
                                                             // sécurisé

    @Get
    @Url(road_url = "/admin/login")
    public ModelAndView loginForm() throws Exception {
        ModelAndView mv = new ModelAndView("/back-office/login.jsp");
        return mv;
    }

    @Post
    @Url(road_url = "/admin/login")
    public ModelAndView login(
            @Param(name = "username") String username,
            @Param(name = "password") String password,
            CustomSession session) throws Exception {

        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
            session.addSession("adminUser", username);
            return new ModelAndView("redirect:/admin/vols");
        } else {
            ModelAndView mv = new ModelAndView("/back-office/login.jsp");
            mv.add_data("error", "Nom d'utilisateur ou mot de passe incorrect");
            return mv;
        }
    }

    @Get
    @Url(road_url = "/admin/logout")
    public ModelAndView logout(CustomSession session) throws Exception {
        session.removeAttribute("adminUser");
        return new ModelAndView("redirect:/admin/login");
    }
}