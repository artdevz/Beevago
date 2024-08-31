package com.Beevago.App.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Beevago.App.enums.ERoomType;
import com.Beevago.App.exceptions.AttributeExistsException;
import com.Beevago.App.models.RoomModel;
import com.Beevago.App.repositories.RoomRepository;

@Service
public class RoomService {
    
    @Autowired
    RoomRepository rr;

    public void saveRoom(RoomModel room) throws Exception {
        
        try {
        
            if (rr.findRoomInHotel(room.getHotelId(), room.getRoomNumber()) != null) {
                throw new AttributeExistsException("Já existe um Quarto com esse número.");
            }

        } catch (Exception e) {
            
        }

        rr.save(room);
    }

    public List<RoomModel> findAllRooms() {
        return rr.findAll();
    }

    public List<RoomModel> findAllRooms(String hotelCity, ERoomType roomType, int personCapacity, double maximumPrice) {
        return rr.findAllRooms(hotelCity, roomType, personCapacity, maximumPrice);
    }

    public List<RoomModel> findAllRoomsInTheHotel(UUID id) {
        return rr.findAllRoomsInTheHotel(id);
    }

    public double findPriceById(UUID id) {
        return rr.findPriceById(id);
    }

    public int findCapacityById(UUID id) {
        return rr.findCapacityById(id);
    }

}
