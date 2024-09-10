package com.Beevago.App.services;

import java.sql.Date;
import java.util.ArrayList;
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

    @Autowired
    ReservationService rs;

    public void saveRoom(RoomModel room) throws Exception {
        
        if (  rr.findRoomInHotel( room.getHotelId(), room.getRoomNumber() ) != null ) {
            throw new AttributeExistsException("Já existe um Quarto com esse número.");
        }

        rr.save(room);
    }

    public List<RoomModel> findAllRooms() {
        return rr.findAll();
    }

    public List<RoomModel> findAllRooms(String hotelCity, ERoomType roomType, int personCapacity, double maximumPrice, Date checkInDate, Date checkOutDate) {

        List<RoomModel> rooms = rr.findAllRooms(hotelCity, roomType, personCapacity, maximumPrice);
        List<RoomModel> avaliableRooms = new ArrayList<RoomModel>();
        
        for (RoomModel room : rooms) if ( !( rs.dateConflicts(room.getId(), checkInDate, checkOutDate) ) ) avaliableRooms.add(room);

        return avaliableRooms;
    }

    public List<RoomModel> findAllRoomsInTheHotel(UUID id) {
        return rr.findAllRoomsInTheHotel(id);
    }

    public int findCapacityById(UUID id) {
        return rr.findCapacityById(id);
    }
    
    public double findPriceById(UUID id) {
        return rr.findPriceById(id);
    }

    public void deleteRoomById(UUID id) {
        rr.deleteByRoomId(id);
    }

}
