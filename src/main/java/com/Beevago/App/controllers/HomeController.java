package com.Beevago.App.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.Beevago.App.enums.ERoomType;
import com.Beevago.App.models.RoomModel;
import com.Beevago.App.services.HotelService;
import com.Beevago.App.services.RoomService;

@Controller
public class HomeController {   

    @Autowired
    HotelService hs;

    @Autowired
    RoomService rs;

    @GetMapping("/")
    public ModelAndView getHomePage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("home/index");
        List<RoomModel> rooms = rs.findAllRooms();
        mv.addObject("RoomsList", rooms);
        mv.addObject("categoriesList", ERoomType.values());
        mv.addObject("stringSearch", ":");           
        return mv;
    }

    @GetMapping("/error")
    public ModelAndView error() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/error");
        return mv;
    }

}
