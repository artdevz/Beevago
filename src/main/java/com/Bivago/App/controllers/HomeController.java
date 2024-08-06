package com.Bivago.App.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public ModelAndView getHomePage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("home/index");
        return mv;
    }

    @GetMapping("/error")
    public ModelAndView error() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/error");
        return mv;
    }

}
