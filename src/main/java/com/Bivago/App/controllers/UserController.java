package com.Bivago.App.controllers;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Bivago.App.exceptions.ChangingUserNameLengthException;
import com.Bivago.App.exceptions.PasswordLengthException;
import com.Bivago.App.exceptions.ServicException;
import com.Bivago.App.models.UserModel;
import com.Bivago.App.services.UserService;
import com.Bivago.App.utils.UtilPassword;

//import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
//import jakarta.validation.Valid;

@Controller
public class UserController {  

    @Autowired
    private UserService us;

    // Cadastro:
    @GetMapping("/cadastro")
    public ModelAndView getCadastrarPage() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("user", new UserModel());
        mv.setViewName("register/index");
        return mv;
    }

    @PostMapping("/cadastro/cadastrando")
    public ModelAndView cadastrarUser(UserModel user, BindingResult result, HttpSession session, RedirectAttributes attributes) throws Exception {
        ModelAndView mv = new ModelAndView();

        if (result.hasErrors()) {
            attributes.addFlashAttribute("msg_erro", "ERRO! Verifique se há campos em branco.");
            mv.setViewName("redirect:/cadastro");
            return mv;
        }

        us.saveUser(user);
        session.setAttribute("usuarioLogado", user);
        attributes.addFlashAttribute("msg", "Cadastro feito com Sucesso!");        
        mv.setViewName("redirect:/");
        return mv;
    }





    // Login:
    @GetMapping("/login")
    public ModelAndView getLoginPage() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("user", new UserModel());
        mv.setViewName("login/index");
        return mv;
    }

    @PostMapping("login/logando")
    public ModelAndView login(UserModel user, BindingResult result, HttpSession session, RedirectAttributes attributes) throws NoSuchAlgorithmException, ServicException {
        ModelAndView mv = new ModelAndView();        
        mv.addObject("user", new UserModel());

        if (result.hasErrors()) {
            mv.setViewName("login/index");
        }

        UserModel userLogin = us.loginUser(user.getUserEmail(), UtilPassword.md5(user.getUserPassword()));

        if (userLogin != null) {
            session.setAttribute("usuarioLogado", userLogin);
            attributes.addFlashAttribute("msg", "Login feito com Sucesso!");        
            mv.setViewName("redirect:/");            
            return mv;            
        } 
        
        mv.addObject("msg", "Usuário não encontrado.");
        mv.setViewName("redirect:/login");    

        return mv;
    }

    // Logout:
    @PostMapping("logout")
    public ModelAndView logout(HttpSession session) {
        session.invalidate();
        return getLoginPage();
    }





    // Configurações:
    @GetMapping("/settings")
    //@RolesAllowed("USER")
    //@PostAuthorize("hasRole('USER')")
    public ModelAndView userSettings(HttpServletRequest request) throws ServletException {
        ModelAndView mv = new ModelAndView();         
        
        

        // if (request.isUserInRole("USER")) {
            mv.setViewName("settings/index");
            //return mv;
        //}
        //mv.setViewName("redirect:/login");              

        return mv;
    }
    




    @GetMapping("/settings/changingusername")
    public ModelAndView userChangingName(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("user", new UserModel());
        mv.setViewName("settings/changingname");
        return mv;
    }

    @PostMapping("postchangingusername")
    public ModelAndView userChangingName(UserModel user, BindingResult result, HttpSession session, RedirectAttributes attributes) throws NoSuchAlgorithmException, ServicException, ChangingUserNameLengthException {
        ModelAndView mv = new ModelAndView();
        mv.addObject("user", new UserModel());

        if (result.hasErrors()) {
            mv.setViewName("login/index");
        }
        
        UserModel userLogin = us.loginUser(user.getUserEmail(), UtilPassword.md5(user.getUserPassword()));        

        if (userLogin != null) {
            session.setAttribute("usuarioLogado", userLogin);
            us.changeUserName(userLogin, user.getUserName());
            attributes.addFlashAttribute("msg", "Nome do Usuário renomeado com Sucesso!");        
            mv.setViewName("redirect:/settings");                       
            return mv;            
        }

        mv.addObject("msg", "Usuário não encontrado.");
        mv.setViewName("redirect:/changingusername");
        return mv; 
    }





    @GetMapping("/settings/changinguserpassword")
    public ModelAndView userChangingPassword(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("user", new UserModel());
        mv.setViewName("settings/changingpassword");
        return mv;
    }

    @PostMapping("postchanginguserpassword")
    public ModelAndView userChangingPassword(UserModel user, BindingResult result, HttpSession session, RedirectAttributes attributes) throws NoSuchAlgorithmException, ServicException, PasswordLengthException {
        ModelAndView mv = new ModelAndView();
        mv.addObject("user", new UserModel());

        if (result.hasErrors()) {
            mv.setViewName("login/index");
        }

        UserModel userLogin = us.loginUser(user.getUserEmail(), UtilPassword.md5(user.getUserPassword()));
        
        if (userLogin != null) {
            session.setAttribute("usuarioLogado", userLogin);
            us.changeUserPassword(userLogin, user.getUserConfirmedPassword());
            attributes.addFlashAttribute("msg", "Senha alterada com Sucesso!");        
            mv.setViewName("redirect:/settings");                       
            return mv;            
        }

        mv.setViewName("redirect:/settings");

        return mv;
    }

}
