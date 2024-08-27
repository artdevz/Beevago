package com.Bivago.App.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.Bivago.App.enums.ERole;
import com.Bivago.App.models.UserModel;
import com.Bivago.App.services.UserService;

@Controller
public class AdminController {
    
    @Autowired
    UserService us;

    @GetMapping("/admin")
    public ModelAndView getAdminPage() {
        ModelAndView mv = new ModelAndView();
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
