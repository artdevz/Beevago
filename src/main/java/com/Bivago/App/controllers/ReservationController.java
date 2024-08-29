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
import com.Bivago.App.dto.HotelSearchDTO;
import com.Bivago.App.dto.RoomDTO;
import com.Bivago.App.dto.UserDTO;
import com.Bivago.App.enums.ERoomType;
import com.Bivago.App.models.ReservationModel;
import com.Bivago.App.services.HotelService;
import com.Bivago.App.services.ReservationService;
import com.Bivago.App.models.RoomModel;
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
    public ModelAndView searchHotels(@RequestParam("searchcity") String hotelCity, @RequestParam(value = "categoryfilter", required = false) ERoomType roomType) {                
        ModelAndView mv = new ModelAndView();        
        mv.setViewName("home/index");        
        mv.addObject("hotelCity", new HotelSearchDTO(hotelCity));
        if (hotelCity == null) {
            List<RoomModel> rooms = qs.findAllRoomsWithHotelCity(roomType);
            mv.addObject("RoomsList", rooms);
            mv.addObject("categoriesList", ERoomType.values());
            return mv;
        }        
        if (roomType != null) {
            List<RoomModel> rooms = qs.findAllRoomsWithHotelCity(hotelCity, roomType);
            mv.addObject("RoomsList", rooms);
            mv.addObject("categoriesList", ERoomType.values());
            return mv;
        }
        List<RoomModel> rooms = qs.findAllRoomsWithHotelCity(hotelCity);
        mv.addObject("RoomsList", rooms);
        mv.addObject("categories", ERoomType.values());
        mv.addObject("categoriesList", ERoomType.values());

        return mv;
    }

    @GetMapping("/reservar")
    public ModelAndView getReservaHotelPage(@RequestParam(value = "userid", required = false) UUID userId, @RequestParam("hotelid") UUID hotelId, @RequestParam("roomid") UUID roomId, @RequestParam("roomtype") ERoomType roomType) {
        ModelAndView mv = new ModelAndView();
        if (userId == null) {
            mv.setViewName("redirect:/login"); return mv;
        }
        mv.addObject("user", new UserDTO(userId));        
        mv.addObject("hotel", new HotelDTO(hs.findHotelNameById(hotelId), hotelId));
        mv.addObject("room", new RoomDTO(roomId, roomType));
        mv.addObject("reserva", new ReservationModel());
        mv.setViewName("reserva/reservar");
        return mv;
    }

    @PostMapping("/reservarquarto")
    public ModelAndView reservandoHotel(ReservationModel reserva, @RequestParam("userid") UUID userId, @RequestParam("hotelid") UUID hotelId, @RequestParam("roomid") UUID roomId, @RequestParam("roomtype") ERoomType roomType) {
        ModelAndView mv = new ModelAndView();
        reserva.setUserID(userId);        
        reserva.setHotelID(hotelId); 
        reserva.setRoomID(roomId);       
        reserva.setRoomType(roomType);        
        reserva.setTotalPrice(rs.reservationPriceCalculator(reserva.getQuantidadeDePessoas(), qs.findPriceById(roomId), reserva.getCheckInDate(), reserva.getCheckOutDate()));         
        rs.saveReservation(reserva);
        mv.setViewName("redirect:/");

        return mv;
    }

}
