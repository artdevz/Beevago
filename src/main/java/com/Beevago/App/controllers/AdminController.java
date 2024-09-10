package com.Beevago.App.controllers;

import java.sql.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Beevago.App.dto.LoginDTO;
import com.Beevago.App.dto.RegisterDTO;
import com.Beevago.App.enums.ERole;
import com.Beevago.App.enums.ERoomType;
import com.Beevago.App.models.HotelModel;
import com.Beevago.App.models.RoomModel;
import com.Beevago.App.models.UserModel;
import com.Beevago.App.services.CookieService;
import com.Beevago.App.services.HotelService;
import com.Beevago.App.services.ReservationService;
import com.Beevago.App.services.RoomService;
import com.Beevago.App.services.TokenService;
import com.Beevago.App.services.UserService;
import com.Beevago.App.utils.JwtDecodeToken;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {
    
    @Autowired
    UserService us;

    @Autowired
    HotelService hs;

    @Autowired
    RoomService qs;

    @Autowired
    ReservationService rs;

    @Autowired
    TokenService ts;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/admin")
    public ModelAndView getAdminLoginPage() {
        ModelAndView mv = new ModelAndView();        
        
        mv.addObject("login", new LoginDTO(null, null));
        mv.setViewName("admin/login");

        return mv;
    }

    @PostMapping(path="/admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getAdminHomePage(LoginDTO login, RedirectAttributes attributes, HttpSession session, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/admin/general");

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(login.email(), login.password());
        Authentication authenticate = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        var user = (UserModel) authenticate.getPrincipal();

        if (user.getUserRole() != ERole.ROLE_ADMIN) {
            attributes.addFlashAttribute("errorMessage", "ACESSO NEGADO");
            mv.setViewName("redirect:/admin");
        }

        var jwt = ts.generateToken(user);

        System.out.println("JWT Token: "+jwt);
        session.setAttribute("usuarioLogado", user);
        CookieService.setCookie(response, "JWT", jwt, 86400);

        return mv;

    }

    @GetMapping("/admin/general")
    public ModelAndView getAdminHomePage(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/index");
        
        if (!JwtDecodeToken.getEmailByJwtToken(CookieService.getCookie(request, "JWT")).equals("beevago@gmail.com") ) {
            mv.setViewName("redirect:/");
        }

        ERole[] role = {ERole.ROLE_USER, ERole.ROLE_MOD};
        mv.addObject("newuser", new RegisterDTO("", "", "", new Date(2l), "", "", null));
        mv.addObject("roleList", role);
        mv.addObject("userList", us.findAllUsers());

        mv.addObject("hotel", new HotelModel());
        mv.addObject("hotelList", hs.findAllHotels());

        mv.addObject("room", new RoomModel());
        mv.addObject("roomList", qs.findAllRooms());
        mv.addObject("roomtypes", ERoomType.values());

        mv.addObject("reservationList", rs.findAllReservations());

        return mv;
    }

    // USER
    @PostMapping("/admin/general/user/create")
    public ModelAndView createUser(RegisterDTO register, @RequestParam("roleList") ERole role, RedirectAttributes attributes) throws Exception {
        ModelAndView mv = new ModelAndView();
        
        UserModel user = new UserModel(register.name(), register.email(), register.cpf(), register.birthday(), register.password(), register.confirmedpassword(), role);

        try {
            us.saveUser(user);
        } catch (Exception e) {
            attributes.addFlashAttribute("errorMessage", e.getMessage());
            mv.setViewName("redirect:/admin/general");
            return mv;
        }

        mv.setViewName("redirect:/admin/general");
        return mv;
    }

    @PostMapping("/admin/general/user/delete")
    public ModelAndView deleteUser(@RequestParam("userid") UUID userId) {
        ModelAndView mv = new ModelAndView();
        us.deleteUserById(userId);
        mv.setViewName("redirect:/admin/general");
        return mv;
    }

    // HOTEL
    @PostMapping("/admin/hotel/create")
    public ModelAndView createHotel(HotelModel hotel) throws Exception {
        ModelAndView mv = new ModelAndView();
        hs.saveHotel(hotel);
        mv.setViewName("redirect:/admin/general");
        return mv;
    }

    @PostMapping("/admin/hotel/delete")
    public ModelAndView deleteHotel(@RequestParam("hotelid") UUID hotelId) {
        ModelAndView mv = new ModelAndView();
        hs.deleteHotelById(hotelId);
        mv.setViewName("redirect:/admin/general");
        return mv;
    }    

    // ROOM
    @PostMapping("/admin/room/create")
    public ModelAndView createRoom(RoomModel room) throws Exception {
        ModelAndView mv = new ModelAndView();
        qs.saveRoom(room);
        mv.setViewName("redirect:/admin/general");
        return mv;
    }

    @PostMapping("/admin/room/delete")
    public ModelAndView deleteRoom(@RequestParam("roomid") UUID roomId) {
        ModelAndView mv = new ModelAndView();
        qs.deleteRoomById(roomId);
        mv.setViewName("redirect:/admin/general");
        return mv;
    }

    // RESERVATION
    @PostMapping("/admin/reservation/delete")
    public ModelAndView deleteReservation(@RequestParam("reservationid") UUID reservationId) {
        ModelAndView mv = new ModelAndView();
        rs.deleteReservationById(reservationId);
        mv.setViewName("redirect:/admin/reservation");
        return mv;
    }
    
}
