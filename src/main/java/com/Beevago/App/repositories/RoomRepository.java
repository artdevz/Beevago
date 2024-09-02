package com.Beevago.App.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Beevago.App.enums.ERoomType;
import com.Beevago.App.models.HotelModel;
import com.Beevago.App.models.RoomModel;

import jakarta.transaction.Transactional;

@Repository
public interface RoomRepository extends JpaRepository<RoomModel, Long> {
    
    @Query(nativeQuery = true, value = "SELECT * FROM room WHERE HOTELID = :hotelId AND ROOMNUMBER = :roomNumber")
    public List<HotelModel> findRoomInHotel(UUID hotelId, int roomNumber);    
    
    @Query(nativeQuery = true, value = "SELECT * FROM room WHERE (:hotelCity IS NULL OR room_hotel_city LIKE %:hotelCity%) AND " +
                                        "(:roomType IS NULL OR ROOMTYPE = :roomType) AND " +
                                        "(:personCapacity IS NULL OR ROOMCAPACITY >= :personCapacity) AND " +
                                        "(:maximumPrice IS NULL OR ROOMPRICE <= :maximumPrice)"
                                        )
    public List<RoomModel> findAllRooms(String hotelCity, ERoomType roomType, int personCapacity, double maximumPrice);

    @Query(nativeQuery = true, value = "SELECT * FROM room WHERE HOTELID = :id")
    public List<RoomModel> findAllRoomsInTheHotel(UUID id);
    
    @Query(nativeQuery = true, value = "SELECT ROOMCAPACITY FROM room WHERE ID = :id")
    public int findCapacityById(UUID id);
    
    @Query(nativeQuery = true, value = "SELECT ROOMPRICE FROM room WHERE ID = :id")
    public double findPriceById(UUID id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM room r WHERE r.ID = :id")
    public void deleteByRoomId(UUID id);

}
