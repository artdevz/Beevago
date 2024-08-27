package com.Bivago.App.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.Bivago.App.dto.HotelDTO;
import com.Bivago.App.dto.RoomDTO;
import com.Bivago.App.dto.UserDTO;
import com.Bivago.App.enums.ERoomType;
import com.Bivago.App.models.HotelModel;
import com.Bivago.App.models.ReservationModel;
import com.Bivago.App.services.HotelService;
import com.Bivago.App.services.ReservationService;
import com.Bivago.App.services.RoomService;

@Controller
public class ReservationController {

    @Autowired
    HotelService hs;

    @Autowired
    ReservationService rs;

    @Autowired
    RoomService qs;

    @PostMapping("/buscar")
    public ModelAndView searchHotels(@RequestParam("buscarcidade") String hotelCity) {                
        ModelAndView mv = new ModelAndView();        
        mv.setViewName("home/index");
        // mv.addObject("hotel", new HotelDTO(hotelCity, hs.findHotelIdByCity(hotelCity))); GAMBIARRA KKKKK       
        List<HotelModel> hotels = hs.findAllHotelsByCity(hotelCity);
        mv.addObject("HotelsList", hotels);
        mv.addObject("categories", ERoomType.values());
        return mv;
    }

    @GetMapping("/reservar")
    public ModelAndView getReservaHotelPage(@RequestParam("userid") UUID userId, @RequestParam("hotelid") UUID hotelId, @RequestParam("roomtype") ERoomType roomType) {
        ModelAndView mv = new ModelAndView();        
        mv.addObject("user", new UserDTO(userId));        
        mv.addObject("hotel", new HotelDTO(hs.findHotelNameById(hotelId), hotelId));
        mv.addObject("room", new RoomDTO(roomType));
        mv.addObject("reserva", new ReservationModel());
        mv.setViewName("reserva/reservar");
        return mv;
    }

    @PostMapping("/reservarquarto")
    public ModelAndView reservandoHotel(ReservationModel reserva, @RequestParam("userid") UUID userId, @RequestParam("hotelid") UUID hotelId, @RequestParam("roomtype") ERoomType roomType) {
        ModelAndView mv = new ModelAndView();
        reserva.setUserID(userId);        
        reserva.setHotelID(hotelId);        
        reserva.setRoomType(roomType);        
        reserva.setTotalPrice(rs.reservationPriceCalculator(reserva.getQuantidadeDePessoas(), reserva.getHotelID(), reserva.getRoomType()));        
        rs.saveReservation(reserva);
        mv.setViewName("redirect:/");

        return mv;
    }

}
