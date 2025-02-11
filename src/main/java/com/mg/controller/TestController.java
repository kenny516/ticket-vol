package com.mg.controller;

import Annotation.Controller;
import Annotation.Get;
import Annotation.Url;
import Model.ModelAndView;

@Controller
public class TestController {

    @Get
    @Url(road_url = "/ato")
    public ModelAndView test() {
        ModelAndView mv = new ModelAndView();
        mv.setUrl("test.jsp");
        return mv;
    }

}
