package com.Beevago.App.controllers;

import java.util.UUID;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Beevago.App.enums.ERole;
import com.Beevago.App.exceptions.LengthException;
import com.Beevago.App.exceptions.NewPasswordEqualsException;
import com.Beevago.App.exceptions.ServicException;
import com.Beevago.App.models.UserModel;
import com.Beevago.App.services.CookieService;
import com.Beevago.App.services.UserService;
import com.Beevago.App.utils.JwtDecodeToken;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {  

    @Autowired
    private UserService us;     
    
    @GetMapping("/settings")
    public ModelAndView userSettings(@RequestParam("userid") UUID userId, Model model, RedirectAttributes attributes, HttpServletRequest request) throws ServletException {
        ModelAndView mv = new ModelAndView();
        model.addAttribute("JWT", CookieService.getCookie(request, "JWT"));

        if ( (userId == null) || (CookieService.getCookie(request, "JWT") == null) ) {
            mv.setViewName("redirect:/login"); return mv;
        }

        if ( !( us.findEmailById(userId).equals(JwtDecodeToken.getEmailByJwtToken(CookieService.getCookie(request, "JWT"))) )) {
            attributes.addFlashAttribute("errorMessage", "ACESSO NEGADO");
            mv.setViewName("redirect:/login");
            return mv;
        }

        mv.setViewName("settings/index");
        mv.addObject("roles", ERole.values());       

        return mv;
    }

    @PostMapping("settings/changingusername")
    public ModelAndView userChangingName(@RequestParam("userid") UUID userId, @RequestParam("changingusername") String newUserName, HttpSession session, RedirectAttributes attributes, HttpServletRequest request) throws NoSuchAlgorithmException, ServicException, LengthException {
        ModelAndView mv = new ModelAndView();

        if ( !( us.findEmailById(userId).equals(JwtDecodeToken.getEmailByJwtToken(CookieService.getCookie(request, "JWT"))) )) {
            attributes.addFlashAttribute("errorMessage", "ACESSO NEGADO");
            mv.setViewName("redirect:/login");
            return mv;
        }

        try {
            us.changeUserName(userId, newUserName);
        } catch (Exception e) {
            attributes.addFlashAttribute("newNameErrorMessage", e.getMessage());
            mv.setViewName("redirect:/settings?userid=" + userId);
            return mv;
        }
        
        attributes.addFlashAttribute("msg", "Nome do Usuário renomeado com Sucesso!");       
        
        UserModel userLogin = us.loginUser(us.findEmailById(userId), us.findPasswordById(userId));
        if (userLogin != null) session.setAttribute("usuarioLogado", userLogin);

        mv.setViewName("redirect:/");

        return mv;
    }

    @PostMapping("settings/changinguserpassword")
    public ModelAndView userChangingPassword(@RequestParam("userid") UUID userId, @RequestParam("changinguserpassword") String newUserPassword, HttpSession session, RedirectAttributes attributes, HttpServletRequest request) throws NoSuchAlgorithmException, ServicException, LengthException, NewPasswordEqualsException {
        ModelAndView mv = new ModelAndView();
        
        if ( !( us.findEmailById(userId).equals(JwtDecodeToken.getEmailByJwtToken(CookieService.getCookie(request, "JWT"))) )) {
            attributes.addFlashAttribute("errorMessage", "ACESSO NEGADO");
            mv.setViewName("redirect:/login");
            return mv;
        }

        try {
            us.changeUserPassword(userId, newUserPassword);
        } catch (Exception e) {
            attributes.addFlashAttribute("newPasswordErrorMessage", e.getMessage());
            mv.setViewName("redirect:/settings?userid=" + userId);
            return mv;
        }
        
        attributes.addFlashAttribute("msg", "Nome do Usuário renomeado com Sucesso!");

        UserModel userLogin = us.loginUser(us.findEmailById(userId), us.findPasswordById(userId));
        if (userLogin != null) session.setAttribute("usuarioLogado", userLogin);
        
        mv.setViewName("redirect:/");
        
        return mv;
    }

}
