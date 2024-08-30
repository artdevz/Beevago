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

    // public List<RoomModel> findRoomsOfHotel(UUID hotelUuid) {
    // public List<RoomModel> findRoomsOfHotel() {
    
    //     return rr.findById();

    // }

    public List<RoomModel> findAllRooms() {
        return rr.findAll();
    }

    public List<RoomModel> findAllRoomsWithHotelCity(ERoomType roomType) {
        return rr.findAllRoomsWithRoomType(roomType);
    }

    public List<RoomModel> findAllRoomsWithHotelCity(String hotelCity) {
        return rr.findAllRoomsWithHotelCity(hotelCity);
    }

    public List<RoomModel> findAllRoomsWithHotelCity(String hotelCity, ERoomType roomType) {
        return rr.findAllRoomsWithHotelCityRoomType(hotelCity, roomType);
    }

    public List<RoomModel> findAllRoomsInTheHotel(UUID id) {
        return rr.findAllRoomsInTheHotel(id);
    }

    public UUID findHotelIdByRoomType(ERoomType roomType) {
        return rr.findHotelIdByRoomType(roomType);
    }

    public double findPriceById(UUID id) {
        return rr.findPriceById(id);
    }

    // public ArrayList<Object> findAllRoomsByCity(String hotelCity) {

    // }

}
