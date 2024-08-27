package com.Bivago.App.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Bivago.App.enums.ERoomType;
import com.Bivago.App.models.HotelModel;
import com.Bivago.App.services.HotelService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HotelController {

    @Autowired
    private HotelService hs;
    
    @GetMapping("/adminhotel")
    public ModelAndView getAdminHotelMainPage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("hotel/index");
        mv.addObject("hotel", new HotelModel());
        List<HotelModel> hotels = hs.findAllHotels();
        mv.addObject("HotelsList", hotels);
        mv.addObject("roomtypes", ERoomType.values());
        return mv;
    }

    @PostMapping("/cadastrohotel/cadastrando")
    public ModelAndView cadastrandoHotel(HotelModel hotel, BindingResult result, HttpSession session, RedirectAttributes attributes) throws Exception {
        ModelAndView mv = new ModelAndView();

        if (result.hasErrors()) {
            attributes.addFlashAttribute("msg_erro", "ERRO! Verifique se há campos em branco.");
            mv.setViewName("redirect:/adminhotel/cadastrohotel");            
            return mv;
        }
        
        hs.saveHotel(hotel);        
        session.setAttribute("hotelCadastrado", hotel);
        attributes.addFlashAttribute("msg", "Hotel cadastrado com Sucesso!");        
        mv.setViewName("redirect:/adminhotel");
        return mv;
        
    }

}
