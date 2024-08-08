package com.Bivago.App.services;

import java.util.List;
//import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Bivago.App.models.RoomModel;
import com.Bivago.App.repositories.RoomRepository;

@Service
public class RoomService {
    
    @Autowired
    RoomRepository rr;

    public void saveRoom(RoomModel room) throws Exception {
        // Fazer as Regras de Negócio
        rr.save(room);
    }

    // public List<RoomModel> findRoomsOfHotel(UUID hotelUuid) {
    // public List<RoomModel> findRoomsOfHotel() {
    
    //     return rr.findById();

    // }

    public List<RoomModel> findAllRooms() {
        return rr.findAll();
    }

}
