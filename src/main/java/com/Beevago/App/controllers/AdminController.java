package com.Beevago.App.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.Beevago.App.enums.ERole;
import com.Beevago.App.enums.ERoomType;
import com.Beevago.App.models.HotelModel;
import com.Beevago.App.models.RoomModel;
import com.Beevago.App.models.UserModel;
import com.Beevago.App.services.HotelService;
import com.Beevago.App.services.ReservationService;
import com.Beevago.App.services.RoomService;
import com.Beevago.App.services.UserService;

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

    @GetMapping("/admin")
    public ModelAndView getAdminPage(@RequestParam(value="userid", required = false) UUID userId) {
        ModelAndView mv = new ModelAndView();
        // JWT TOKEN SAVE THIS:
        // if ( !(us.findRoleById(userId).equals(ERole.ROLE_ADMIN)) ) {
        //     mv.setViewName("redirect:/"); return mv;
        // }
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
        mv.addObject("userList", us.findAllUsers());

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
        mv.addObject("hotelList", hs.findAllHotels());

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
        mv.addObject("room", new RoomModel());
        mv.addObject("roomList", qs.findAllRooms());
        mv.addObject("roomtypes", ERoomType.values());
        return mv;
    }

    @PostMapping("/admin/room/create")
    public ModelAndView createRoom(RoomModel room) throws Exception {
        ModelAndView mv = new ModelAndView();
        qs.saveRoom(room);
        mv.setViewName("redirect:/admin/room");
        return mv;
    }

    @PostMapping("/admin/room/delete")
    public ModelAndView deleteRoom(@RequestParam("roomid") UUID roomId) {
        ModelAndView mv = new ModelAndView();
        qs.deleteRoomById(roomId);
        mv.setViewName("redirect:/admin/room");
        return mv;
    }

    @GetMapping("/admin/reservation")
    public ModelAndView getAdminReservationCrud() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/reservationcrud");
        mv.addObject("reservationList", rs.findAllReservations());
        return mv;
    }

    @PostMapping("/admin/reservation/delete")
    public ModelAndView deleteReservation(@RequestParam("reservationid") UUID reservationId) {
        ModelAndView mv = new ModelAndView();
        rs.deleteReservationById(reservationId);
        mv.setViewName("redirect:/admin/reservation");
        return mv;
    }
    
}
