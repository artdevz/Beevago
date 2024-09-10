package com.Beevago.App.controllers;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Beevago.App.dto.LoginDTO;
import com.Beevago.App.dto.RegisterDTO;
import com.Beevago.App.enums.ERole;
import com.Beevago.App.models.UserModel;
import com.Beevago.App.services.CookieService;
import com.Beevago.App.services.TokenService;
import com.Beevago.App.services.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    TokenService ts;

    @Autowired
    UserService us;

    @GetMapping("/register")
    public ModelAndView getCadastrarPage() {
        ModelAndView mv = new ModelAndView();        
        mv.addObject("newuser", new RegisterDTO("Arthur", "arthur@gmail.com", "087.150.553-37", new Date(2l), "null", "null", ERole.ROLE_USER));
        mv.setViewName("register/index");
        return mv;
    }
    
    @PostMapping(path="/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView register(RegisterDTO register, BindingResult result, RedirectAttributes attributes, HttpSession session) throws Exception {
        ModelAndView mv = new ModelAndView();
               
        UserModel user = new UserModel(register.name(), register.email(), register.cpf(), register.birthday(), register.password(), register.confirmedpassword(), register.role());
        
        try {
            us.saveUser(user);
        } catch (Exception e) {
            attributes.addFlashAttribute("errorMessage", e.getMessage());
            mv.setViewName("redirect:/register");
            return mv;
        }
        
        session.setAttribute("usuarioLogado", user);
        mv.setViewName("redirect:/");
        return mv;
    }

    @GetMapping("/login")
    public ModelAndView getLoginPage() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("login", new LoginDTO("null", "null"));
        mv.setViewName("login/index");
        return mv;
    }

    @PostMapping(path="/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView login(LoginDTO login,RedirectAttributes attributes, HttpSession session, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/");
        
        try {

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(login.email(), login.password());
            Authentication authenticate = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            var user = (UserModel) authenticate.getPrincipal();
            var jwt = ts.generateToken(user);            
            session.setAttribute("usuarioLogado", user);
            CookieService.setCookie(response, "JWT", jwt, 86400);

        } catch (Exception e) {
            attributes.addFlashAttribute("errorMessage", "Houve um erro no processo de Login");
            mv.setViewName("redirect:/login");
            return mv;
        }

        return mv;
    }

    @PostMapping("logout")
    public ModelAndView logout(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        session.invalidate();
        mv.setViewName("redirect:/");
        return mv;
    }

}
