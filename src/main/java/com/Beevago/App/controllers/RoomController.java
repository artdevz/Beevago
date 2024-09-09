package com.Beevago.App.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Beevago.App.enums.ERole;
import com.Beevago.App.enums.ERoomType;
import com.Beevago.App.models.RoomModel;
import com.Beevago.App.services.CookieService;
import com.Beevago.App.services.HotelService;
import com.Beevago.App.services.RoomService;
import com.Beevago.App.services.UserService;
import com.Beevago.App.utils.JwtDecodeToken;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class RoomController {

    @Autowired
    RoomService rs;

    @Autowired
    HotelService hs;

    @Autowired
    UserService us;
    
    @GetMapping("/settings/hotelsettings/roomsettings")
    public ModelAndView getRoomSettingsPage(@RequestParam(value="hotelId", required = false) UUID hotelId, Model model, RedirectAttributes attributes, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        model.addAttribute("JWT", CookieService.getCookie(request, "JWT"));
        mv.setViewName("room/index");

        if ( ! ( ((us.findRoleById(hs.findOwnerById(hotelId).getOwnerId()) ).equals(ERole.ROLE_MOD)) || ((us.findRoleById(hs.findOwnerById(hotelId).getOwnerId())).equals(ERole.ROLE_ADMIN))) ) {
            mv.setViewName("redirect:/"); return mv;
        }

        if ( !( (us.findEmailById(hs.findOwnerById(hotelId).getOwnerId())).equals(JwtDecodeToken.getEmailByJwtToken(CookieService.getCookie(request, "JWT"))) )) {
            attributes.addFlashAttribute("errorMessage", "ACESSO NEGADO");
            mv.setViewName("redirect:/login");
            return mv;
        }

        mv.addObject("room", new RoomModel());
        mv.addObject("HotelName", hs.findHotelNameById(hotelId));
        mv.addObject("HotelId", hotelId);
        mv.addObject("RoomsList", rs.findAllRoomsInTheHotel(hotelId));
        mv.addObject("roomtypes", ERoomType.values());             
        return mv;
    }

    @PostMapping("settings/hotelsettings/registerroom")
    public ModelAndView registerRoom(RoomModel room, @RequestParam(value="hotelId", required = false) UUID hotelId, BindingResult result, HttpSession session, RedirectAttributes attributes, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        
        if (result.hasErrors()) {
            attributes.addFlashAttribute("msg_erro", "ERRO! Verifique se há campos em branco.");
            mv.setViewName("redirect:/adminhotel/roomsettings?hotelId=" + hotelId);            
            return mv;
        }

        if ( !( (us.findEmailById(hs.findOwnerById(hotelId).getOwnerId())).equals(JwtDecodeToken.getEmailByJwtToken(CookieService.getCookie(request, "JWT"))) )) {
            attributes.addFlashAttribute("errorMessage", "ACESSO NEGADO");
            mv.setViewName("redirect:/login");
            return mv;
        }

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
