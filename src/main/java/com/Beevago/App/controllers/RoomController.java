package com.Beevago.App.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Beevago.App.enums.ERoomType;
import com.Beevago.App.models.RoomModel;
import com.Beevago.App.services.HotelService;
import com.Beevago.App.services.RoomService;

import jakarta.servlet.http.HttpSession;

@Controller
public class RoomController {

    @Autowired
    RoomService rs;

    @Autowired
    HotelService hs;
    
    @GetMapping("/settings/hotelsettings/roomsettings")
    public ModelAndView getRoomSettingsPage(@RequestParam(value="hotelId", required = false) UUID hotelId) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("room/index");
        mv.addObject("room", new RoomModel());
        mv.addObject("HotelName", hs.findHotelNameById(hotelId));
        mv.addObject("HotelId", hotelId);
        mv.addObject("RoomsList", rs.findAllRoomsInTheHotel(hotelId));
        mv.addObject("roomtypes", ERoomType.values());             
        return mv;
    }

    @PostMapping("/cadastrarquarto")
    public ModelAndView cadastrarQuarto(RoomModel room, @RequestParam(value="hotelId", required = false) UUID hotelId, BindingResult result, HttpSession session, RedirectAttributes attributes) throws Exception {
        ModelAndView mv = new ModelAndView();
        
        // if (result.hasErrors()) {
        //     attributes.addFlashAttribute("msg_erro", "ERRO! Verifique se há campos em branco.");
        //     mv.setViewName("redirect:/adminhotel/roomsettings");            
        //     return mv;
        // }

        room.setHotelId(hotelId);
        room.setRoomHotelName(hs.findHotelNameById(hotelId));
        room.setRoomHotelAddress(hs.findHotelAddressById(hotelId));
        room.setRoomHotelCity(hs.findHotelCityById(hotelId));

        try {
            rs.saveRoom(room);
        } catch (Exception e) {
            attributes.addFlashAttribute("errorMessage", e.getMessage());
            mv.setViewName("redirect:/settings/hotelsettings/roomsettings?hotelId=" + hotelId);
            return mv;
        }
        
        session.setAttribute("quartoCadastrado", room);
        attributes.addFlashAttribute("msg", "Quarto cadastrado com Sucesso!");        
        mv.setViewName("redirect:/settings/hotelsettings/roomsettings?hotelId=" + hotelId);

        return mv;
    }

}
