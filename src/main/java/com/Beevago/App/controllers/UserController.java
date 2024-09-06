package com.Beevago.App.controllers;

import java.util.UUID;

//import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PostAuthorize;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
import com.Beevago.App.services.UserService;
import com.Beevago.App.utils.UtilPassword;

//import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {  

    @Autowired
    private UserService us;

    // // Cadastro:
    // @GetMapping("/cadastro")
    // public ModelAndView getRegisterPage() {
    //     ModelAndView mv = new ModelAndView();
    //     mv.addObject("user", new UserModel());
    //     mv.setViewName("register/index");
    //     return mv;
    // }

    // @PostMapping("/cadastro/cadastrando")
    // public ModelAndView cadastrarUser(@Valid UserModel user, BindingResult result, RedirectAttributes attributes, HttpSession session) throws Exception {
    //     ModelAndView mv = new ModelAndView();

    //     if (result.hasErrors()) {
    //         attributes.addFlashAttribute("errorMessage", "Erro! Verifique se há campos em branco.");
    //         mv.setViewName("redirect:/cadastro");
    //         return mv;
    //     }
        
    //     try {
    //         us.saveUser(user); 
    //     } catch (Exception e) {            
    //         attributes.addFlashAttribute("errorMessage", e.getMessage());
    //         mv.setViewName("redirect:/cadastro");
    //         return mv;
    //     }
        
    //     session.setAttribute("usuarioLogado", user);
    //     mv.setViewName("redirect:/");
    //     return mv;
    // }

    // Login:
    

    // @PostMapping("login/logando")
    // public ModelAndView login(UserModel user, BindingResult result, HttpSession session, RedirectAttributes attributes) throws NoSuchAlgorithmException, ServicException {
    //     ModelAndView mv = new ModelAndView();        
    //     mv.addObject("user", new UserModel());

    //     if (result.hasErrors()) {
    //         attributes.addFlashAttribute("msg", "Usuário não encontrado.");
    //         mv.setViewName("login/index");
    //     }

    //     UserModel userLogin = us.loginUser(user.getUserEmail(), UtilPassword.md5(user.getUserPassword()));

    //     if (userLogin != null) {
    //         session.setAttribute("usuarioLogado", userLogin);
    //         attributes.addFlashAttribute("msg", "Login feito com Sucesso!");        
    //         mv.setViewName("redirect:/");            
    //         return mv;            
    //     } 
        
    //     mv.addObject("msg", "Usuário não encontrado.");
    //     mv.setViewName("redirect:/login");    

    //     return mv;
    // }

    // Logout:
    @PostMapping("logout")
    public ModelAndView logout(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        session.invalidate();
        mv.setViewName("redirect:/");
        return mv;
    }

    // Configurações:
    @GetMapping("/settings")
    public ModelAndView userSettings(@RequestParam(value="userid", required = false) UUID userId, HttpServletRequest request) throws ServletException {
        ModelAndView mv = new ModelAndView();
        if (userId == null) {
            mv.setViewName("redirect:/login"); return mv;
        }
        mv.setViewName("settings/index");
        mv.addObject("user", us.findUserById(userId));
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
