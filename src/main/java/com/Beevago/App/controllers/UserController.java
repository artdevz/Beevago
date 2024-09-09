package com.Beevago.App.controllers;

import java.util.UUID;

//import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Decoder;

@Controller
public class UserController {  

    @Autowired
    private UserService us;  
    
    @GetMapping("/settings")
    public ModelAndView userSettings(@RequestParam("userid") UUID userId, Model model, HttpServletRequest request) throws ServletException {
        ModelAndView mv = new ModelAndView();
        model.addAttribute("JWT", CookieService.getCookie(request, "JWT"));
        System.out.println("Chegou");
        String[] chunks = CookieService.getCookie(request, "JWT").split("\\.");       

        String base64EncodedBody = chunks[1];       
        @SuppressWarnings("deprecation")
        Base64 base64Url = new Base64(true);
        String body = new String(base64Url.decode(base64EncodedBody));
        System.out.println(body);
        String[] bodyPiece = body.split(":");
        String[] bodyPieceTwo = bodyPiece[2].split(",");        
        System.out.println(bodyPieceTwo[0].replace("\"", ""));

        // if (userId == null) {
        //     mv.setViewName("redirect:/login"); return mv;
        // }

        if (!(us.findEmailById(userId).equals(bodyPieceTwo[0].replace("\"", "")))) {
            System.out.println("Não");
            mv.setViewName("redirect:/");
            return mv;
        }
        
        mv.setViewName("settings/index");
        // mv.addObject("user", us.findUserById(userId));
        mv.addObject("roles", ERole.values());       

        return mv;
    }

    @PostMapping("settings/changingusername")
    public ModelAndView userChangingName(@RequestParam("userid") UUID userId, @RequestParam("changingusername") String newUserName, HttpSession session, RedirectAttributes attributes) throws NoSuchAlgorithmException, ServicException, LengthException {
        ModelAndView mv = new ModelAndView();

        try {
            us.changeUserName(userId, newUserName);
        } catch (Exception e) {
            attributes.addFlashAttribute("newNameErrorMessage", e.getMessage());
            mv.setViewName("redirect:/settings?userid=" + userId);
            return mv;
        }
        
        attributes.addFlashAttribute("msg", "Nome do Usuário renomeado com Sucesso!");        
        
        // JWT TOKEN SAVE THIS:
        UserModel userLogin = us.loginUser(us.findEmailById(userId), us.findPasswordById(userId));
        if (userLogin != null) session.setAttribute("usuarioLogado", userLogin);

        mv.setViewName("redirect:/");

        return mv;
    }

    @PostMapping("settings/changinguserpassword")
    public ModelAndView userChangingPassword(@RequestParam("userid") UUID userId, @RequestParam("changinguserpassword") String newUserPassword, HttpSession session, RedirectAttributes attributes) throws NoSuchAlgorithmException, ServicException, LengthException, NewPasswordEqualsException {
        ModelAndView mv = new ModelAndView();
        
        try {
            us.changeUserPassword(userId, newUserPassword);
        } catch (Exception e) {
            attributes.addFlashAttribute("newPasswordErrorMessage", e.getMessage());
            mv.setViewName("redirect:/settings?userid=" + userId);
            return mv;
        }
        
        attributes.addFlashAttribute("msg", "Nome do Usuário renomeado com Sucesso!");

        // JWT TOKEN SAVE THIS:
        UserModel userLogin = us.loginUser(us.findEmailById(userId), us.findPasswordById(userId));
        if (userLogin != null) session.setAttribute("usuarioLogado", userLogin);
        
        mv.setViewName("redirect:/");
        
        return mv;
    }

}
