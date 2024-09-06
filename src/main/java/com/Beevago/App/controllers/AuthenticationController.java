package com.Beevago.App.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.Beevago.App.dto.AuthenticationDTO;
import com.Beevago.App.dto.LoginResponseDTO;
import com.Beevago.App.dto.RegisterDTO;
import com.Beevago.App.models.UserModel;
import com.Beevago.App.repositories.UserRepository;
import com.Beevago.App.services.TokenService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("auth")
public class AuthenticationController {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository ur;

    @Autowired
    private TokenService ts;

    @GetMapping("/register")
    public ModelAndView getRegisterPage() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("user", new UserModel());
        mv.setViewName("register/index");
        return mv;
    }
    // consumes = "application/x-www-form-urlencoded", 
    @SuppressWarnings("rawtypes")
    @PostMapping(path = "/register", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        System.out.println(data.name());
        System.out.println(data.login());
        System.out.println(data.cpf());
        System.out.println(data.birthday());
        System.out.println(data.password());
        System.out.println(data.role());
        if (this.ur.findByUserEmail(data.login()) != null) return ResponseEntity.badRequest().build();        
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        UserModel newUser = new UserModel(data.name(), data.cpf(), data.login(), data.birthday(), encryptedPassword, data.role());
    
        this.ur.save(newUser);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/login")
    public ModelAndView getLoginPage() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("user", new UserModel());
        mv.setViewName("login/index");
        return mv;
    }

    @SuppressWarnings("rawtypes")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        
        var token = ts.generateToken((UserModel) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

}
