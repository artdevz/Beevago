package com.Bivago.App.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Bivago.App.enums.ERoomType;
import com.Bivago.App.exceptions.AttributeExistsException;
import com.Bivago.App.models.RoomModel;
import com.Bivago.App.repositories.RoomRepository;

@Service
public class RoomService {
    
    @Autowired
    RoomRepository rr;

    public void saveRoom(RoomModel room) throws Exception {
        
        try {
        
            if (rr.findRoomInHotel(room.getHotelRoom(), room.getRoomNumber()) != null) {
                throw new AttributeExistsException("Já existe um Quarto com esse número.");
            }

        } catch (Exception e) {
            
        }

        rr.save(room);
    }

    // public List<RoomModel> findRoomsOfHotel(UUID hotelUuid) {
    // public List<RoomModel> findRoomsOfHotel() {
    
    //     return rr.findById();

    // }

    public List<RoomModel> findAllRooms() {
        return rr.findAll();
    }

    public List<RoomModel> findAllRoomsInTheHotel(UUID id) {
        return rr.findAllRoomsInTheHotel(id);
    }

    public UUID findHotelIdByRoomType(ERoomType roomType) {
        return rr.findHotelIdByRoomType(roomType);
    }

    // public ArrayList<Object> findAllRoomsByCity(String hotelCity) {

    // }

}
