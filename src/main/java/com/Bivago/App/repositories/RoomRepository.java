package com.Bivago.App.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Bivago.App.enums.ERoomType;
import com.Bivago.App.models.HotelModel;
import com.Bivago.App.models.RoomModel;

@Repository
public interface RoomRepository extends JpaRepository<RoomModel, Long> {
    
    @Query(nativeQuery = true, value = "SELECT * FROM room WHERE HOTEL = :hotel AND ROOMNUMBER = :roomNumber")
    public List<HotelModel> findRoomInHotel(HotelModel hotel, int roomNumber);

    @Query(nativeQuery = true, value = "SELECT * FROM room WHERE HOTELROOM = :id")
    public List<RoomModel> findAllRoomsInTheHotel(UUID id);

    @Query(nativeQuery = true, value = "SELECT * FROM room WHERE ROOMTYPE = :roomType")
    public UUID findHotelIdByRoomType(ERoomType roomType);

    // @Query(nativeQuery = true, value = "")
    // public List<RoomModel> findById();

}
