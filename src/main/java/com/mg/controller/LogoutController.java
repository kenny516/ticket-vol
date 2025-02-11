package com.mg.controller;

import Annotation.Controller;
import Annotation.Get;
import Annotation.Url;
import Model.CustomSession;

@Controller
public class LogoutController{

    @Get
    @Url(road_url = "/logout")
    public String logout(CustomSession customSession){
        customSession.destroySession();
        return "/";
    }
}