package com.Beevago.App.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Beevago.App.dto.HotelDTO;
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
    
    @GetMapping("adminhotel/roomsettings/{id}")
    public ModelAndView getRoomSettingsPage(@PathVariable UUID id) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("room/index");                  
        mv.addObject("room", new RoomModel());
        mv.addObject("HotelName", new HotelDTO(hs.findHotelNameById(id), id));
        List<RoomModel> rooms = rs.findAllRoomsInTheHotel(id);
        mv.addObject("RoomsList", rooms);
        mv.addObject("roomtypes", ERoomType.values());             
        return mv;
    }

    @PostMapping("cadastrarquarto/{id}")
    public ModelAndView cadastrarQuarto(RoomModel room, @PathVariable UUID id, BindingResult result, HttpSession session, RedirectAttributes attributes) throws Exception {
        ModelAndView mv = new ModelAndView();
        
        // if (result.hasErrors()) {
        //     attributes.addFlashAttribute("msg_erro", "ERRO! Verifique se há campos em branco.");
        //     mv.setViewName("redirect:/adminhotel/roomsettings");            
        //     return mv;
        // }

        room.setHotelId(id);
        room.setRoomHotelName(hs.findHotelNameById(id));
        room.setRoomHotelAddress(hs.findHotelAddressById(id));
        room.setRoomHotelCity(hs.findHotelCityById(id));
        rs.saveRoom(room);
        session.setAttribute("quartoCadastrado", room);
        attributes.addFlashAttribute("msg", "Quarto cadastrado com Sucesso!");        
        mv.setViewName("redirect:/adminhotel/roomsettings/{id}");

        return mv;
    }

}
