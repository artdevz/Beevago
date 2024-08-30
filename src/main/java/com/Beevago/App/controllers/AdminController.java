package com.Beevago.App.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.Beevago.App.enums.ERole;
import com.Beevago.App.models.HotelModel;
import com.Beevago.App.models.UserModel;
import com.Beevago.App.services.HotelService;
import com.Beevago.App.services.UserService;

@Controller
public class AdminController {
    
    @Autowired
    UserService us;

    @Autowired
    HotelService hs;

    @GetMapping("/admin")
    public ModelAndView getAdminPage() {
        ModelAndView mv = new ModelAndView();
        // FAZER: SOMENTE ADMIN PODER ENTRAR
        mv.setViewName("admin/index");
        return mv;
    }

    @GetMapping("/admin/user")
    public ModelAndView getAdminUserCrud() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/usercrud");
        mv.addObject("user", new UserModel());
        ERole[] role = {ERole.ROLE_USER, ERole.ROLE_MOD};
        mv.addObject("roleList", role);
        List<UserModel> userList = us.findAllUsers();
        mv.addObject("userList", userList);

        return mv;
    }

    @PostMapping("/admin/user/create")
    public ModelAndView createUser(UserModel user, @RequestParam("roleList") ERole role) throws Exception {
        ModelAndView mv = new ModelAndView();
        user.setUserRole(role);
        us.saveUser(user);
        mv.setViewName("redirect:/admin/user");
        return mv;
    }

    @PostMapping("/admin/user/delete")
    public ModelAndView deleteUser(@RequestParam("userid") UUID userId) {
        ModelAndView mv = new ModelAndView();
        us.deleteUserById(userId);
        mv.setViewName("redirect:/admin/user");
        return mv;
    }

    @GetMapping("/admin/hotel")
    public ModelAndView getAdminHotelCrud() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/hotelcrud");
        mv.addObject("hotel", new HotelModel());
        List<HotelModel> hotelList = hs.findAllHotels();
        mv.addObject("hotelList", hotelList);

        return mv;
    }

    @PostMapping("/admin/hotel/create")
    public ModelAndView createHotel(HotelModel hotel) throws Exception {
        ModelAndView mv = new ModelAndView();
        hs.saveHotel(hotel);
        mv.setViewName("redirect:/admin/hotel");
        return mv;
    }

    @PostMapping("/admin/hotel/delete")
    public ModelAndView deleteHotel(@RequestParam("hotelid") UUID hotelId) {
        ModelAndView mv = new ModelAndView();
        hs.deleteHotelById(hotelId);
        mv.setViewName("redirect:/admin/hotel");
        return mv;
    }

    @GetMapping("/admin/room")
    public ModelAndView getAdminRoomCrud() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/roomcrud");
        return mv;
    }

    @GetMapping("/admin/reservation")
    public ModelAndView getAdminReservationCrud() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/reservationcrud");
        return mv;
    }
    
}
