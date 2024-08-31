package com.Beevago.App.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.Beevago.App.dto.HotelDTO;
import com.Beevago.App.dto.RoomDTO;
import com.Beevago.App.dto.UserDTO;
import com.Beevago.App.enums.ERoomType;
import com.Beevago.App.models.ReservationModel;
import com.Beevago.App.models.RoomModel;
import com.Beevago.App.services.HotelService;
import com.Beevago.App.services.ReservationService;
import com.Beevago.App.services.RoomService;

@Controller
public class ReservationController {

    @Autowired
    HotelService hs;

    @Autowired
    ReservationService rs;

    @Autowired
    RoomService qs;

    @PostMapping("/buscar")
    public ModelAndView searchHotels(
        @RequestParam(value = "searchcity", required = false) String hotelCity,
        @RequestParam(value = "categoryfilter", required = false) ERoomType roomType,
        @RequestParam(value = "searchperson", required = false) int personCapacity,
        @RequestParam(value = "searchprice", required = false) double maximumPrice
        ) {

        ModelAndView mv = new ModelAndView();        
        mv.setViewName("home/index");        
        mv.addObject("categoriesList", ERoomType.values());

        String sRoomType = (roomType != null)? roomType.getRoomType() : "Todas as Categorias";
        String sHotelCity = (hotelCity != "")? hotelCity : "Todas as Cidades";
        mv.addObject("stringSearch", "em " + sHotelCity + ", " + sRoomType + ", " + personCapacity + " Pessoa(s)" + ", Até R$"+ maximumPrice + ":");

        List<RoomModel> rooms = qs.findAllRooms(hotelCity, roomType, personCapacity, maximumPrice);
        mv.addObject("RoomsList", rooms);        

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

        if (rs.dateConflicts(roomId, reserva.getCheckInDate(), reserva.getCheckOutDate())) {
            // System.out.println("Ocupado");
            mv.setViewName("redirect:/");
            return mv;
        }

        if (qs.findCapacityById(roomId) < reserva.getQuantidadeDePessoas()) {
            // System.out.println("Acima da Capacidade");
            mv.setViewName("redirect:/");
            return mv;
        }

        reserva.setUserID(userId);        
        reserva.setHotelID(hotelId); 
        reserva.setRoomID(roomId);       
        reserva.setRoomType(roomType);
        reserva.setTotalPrice( rs.daysInRoom(reserva.getCheckInDate(), reserva.getCheckOutDate()) * reserva.getQuantidadeDePessoas() * qs.findPriceById(roomId) );
                 
        rs.saveReservation(reserva);
        // System.out.println("Sucesso");        
        mv.setViewName("redirect:/");

        return mv;
    }

}
