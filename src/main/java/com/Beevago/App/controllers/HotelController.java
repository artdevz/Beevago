package com.Beevago.App.controllers;

import java.util.List;
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
import com.Beevago.App.models.HotelModel;
import com.Beevago.App.services.CookieService;
import com.Beevago.App.services.HotelService;
import com.Beevago.App.services.UserService;
import com.Beevago.App.utils.JwtDecodeToken;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HotelController {

    @Autowired
    private HotelService hs;

    @Autowired
    private UserService us;
    
    @GetMapping("settings/hotelsettings")
    public ModelAndView getAdminHotelMainPage(@RequestParam(value="userid", required = false) UUID userId, Model model, RedirectAttributes attributes, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        model.addAttribute("JWT", CookieService.getCookie(request, "JWT"));

        if ( !(us.findRoleById(userId).equals(ERole.ROLE_MOD) || us.findRoleById(userId).equals(ERole.ROLE_ADMIN)) ) {
            attributes.addFlashAttribute("errorMessage", "ACESSO NEGADO");
            mv.setViewName("redirect:/login");
            return mv;
        }

        if ( !( us.findEmailById(userId).equals(JwtDecodeToken.getEmailByJwtToken(CookieService.getCookie(request, "JWT"))) )) {
            attributes.addFlashAttribute("errorMessage", "ACESSO NEGADO");
            mv.setViewName("redirect:/login");
            return mv;
        }

        mv.setViewName("hotel/index");
        mv.addObject("userId", userId);
        mv.addObject("hotel", new HotelModel());
        List<HotelModel> hotels = hs.findAllHotelsWithUserId(userId);
        mv.addObject("HotelsList", hotels);
        mv.addObject("roomtypes", ERoomType.values());
        return mv;
    }

    @PostMapping("settings/hotelsettings/registerhotel")
    public ModelAndView registerHotel(HotelModel hotel, @RequestParam("userid") UUID userId, Model model, BindingResult result, HttpSession session, RedirectAttributes attributes, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        model.addAttribute("JWT", CookieService.getCookie(request, "JWT"));

        if (result.hasErrors()) {
            attributes.addFlashAttribute("errorMessage", "ERRO! Verifique se há campos em branco.");
            mv.setViewName("redirect:/settings/hotelsettings?userid=" + userId);            
            return mv;
        }
        
        if ( !( us.findEmailById(userId).equals(JwtDecodeToken.getEmailByJwtToken(CookieService.getCookie(request, "JWT"))) )) {
            attributes.addFlashAttribute("errorMessage", "ACESSO NEGADO");
            mv.setViewName("redirect:/login");
            return mv;
        }
        
        hotel.setOwnerId(userId);

        try {
            hs.saveHotel(hotel);
        } catch (Exception e) {
            attributes.addFlashAttribute("errorMessage", e.getMessage());
            mv.setViewName("redirect:/settings/hotelsettings?userid=" + userId);
            return mv;
        }        
                
        session.setAttribute("hotelCadastrado", hotel);
        attributes.addFlashAttribute("msg", "Hotel cadastrado com Sucesso!");        
        mv.setViewName("redirect:/settings/hotelsettings?userid=" + userId);
        return mv;
        
    }

}
