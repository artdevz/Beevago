package com.Bivago.App.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.Bivago.App.enums.ERoomType;
import com.Bivago.App.models.HotelModel;
import com.Bivago.App.services.HotelService;

@Controller
public class HomeController {   

    @Autowired
    HotelService hs;

    @GetMapping("/")
    public ModelAndView getHomePage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("home/index");
        List<HotelModel> hotels = hs.findAllHotels();        
        mv.addObject("HotelsList", hotels);
        mv.addObject("categories", ERoomType.values());           
        return mv;
    }

    @GetMapping("/error")
    public ModelAndView error() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/error");
        return mv;
    }

}
