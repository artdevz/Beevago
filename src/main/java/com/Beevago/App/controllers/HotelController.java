package com.Beevago.App.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Beevago.App.enums.ERole;
import com.Beevago.App.enums.ERoomType;
import com.Beevago.App.models.HotelModel;
import com.Beevago.App.services.HotelService;
import com.Beevago.App.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HotelController {

    @Autowired
    private HotelService hs;

    @Autowired
    private UserService us;
    
    @GetMapping("settings/hotelsettings")
    public ModelAndView getAdminHotelMainPage(@RequestParam(value="userid", required = false) UUID userId) {
        ModelAndView mv = new ModelAndView();

        if ( !(us.findRoleById(userId).equals(ERole.ROLE_MOD) || us.findRoleById(userId).equals(ERole.ROLE_ADMIN)) ) {
            mv.setViewName("redirect:/"); return mv;
        }

        mv.setViewName("hotel/index");
        mv.addObject("hotel", new HotelModel());
        List<HotelModel> hotels = hs.findAllHotelsWithUserId(userId);
        mv.addObject("HotelsList", hotels);
        mv.addObject("roomtypes", ERoomType.values());
        return mv;
    }

    @PostMapping("/cadastrohotel/cadastrando")
    public ModelAndView cadastrandoHotel(HotelModel hotel, @RequestParam("userid") UUID userId, BindingResult result, HttpSession session, RedirectAttributes attributes) throws Exception {
        ModelAndView mv = new ModelAndView();

        if (result.hasErrors()) {
            attributes.addFlashAttribute("msg_erro", "ERRO! Verifique se há campos em branco.");
            mv.setViewName("redirect:/adminhotel/cadastrohotel");            
            return mv;
        }
        
        hotel.setOwnerId(userId);
        hs.saveHotel(hotel);        
        session.setAttribute("hotelCadastrado", hotel);
        attributes.addFlashAttribute("msg", "Hotel cadastrado com Sucesso!");        
        mv.setViewName("redirect:/settings/hotelsettings?userid=" + userId);
        return mv;
        
    }

}
