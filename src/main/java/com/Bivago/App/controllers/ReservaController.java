package com.Bivago.App.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.Bivago.App.models.HotelModel;
import com.Bivago.App.models.ReservaModel;
import com.Bivago.App.models.UserModel;
import com.Bivago.App.services.HotelService;

@Controller
public class ReservaController {

    @Autowired
    HotelService hs;

    @GetMapping("/buscar")
    public ModelAndView buscarHoteis() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("reserva/index");        
        List<HotelModel> hotels = hs.findAllHotels();
        mv.addObject("HotelsList", hotels);
        return mv;
    }

    @GetMapping("/reservar")
    public ModelAndView getReservaHotelPage() {
        ModelAndView mv = new ModelAndView();
        // Login, Reservar, CheckIn, CheckOut
        mv.addObject("user", new UserModel());
        mv.addObject("hotel", new HotelModel());
        mv.addObject("reserva", new ReservaModel());
        mv.setViewName("reserva/reservar");
        return mv;
    }

    @PostMapping("reservar/reservando")
    public ModelAndView reservandoHotel() {
        ModelAndView mv = new ModelAndView();
        
        return mv;
    }

}
