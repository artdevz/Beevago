package com.Bivago.App.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.Bivago.App.models.HotelModel;
import com.Bivago.App.models.ReservationModel;
import com.Bivago.App.models.UserModel;
import com.Bivago.App.services.HotelService;
import com.Bivago.App.services.ReservationService;

@Controller
public class ReservationController {

    @Autowired
    HotelService hs;

    @Autowired
    ReservationService rs;

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
        mv.addObject("reserva", new ReservationModel());
        mv.setViewName("reserva/reservar");
        return mv;
    }

    @PostMapping("reservar/reservando")
    public ModelAndView reservandoHotel(ReservationModel reserva) {
        ModelAndView mv = new ModelAndView();
        
        reserva.setTotalPrice(rs.reservationPriceCalculator(reserva.getQuantidadeDePessoas(), reserva.getRoomType()));

        rs.saveReservation(reserva);

        return mv;
    }

}
