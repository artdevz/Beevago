package com.Bivago.App.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Bivago.App.enums.ERoomType;
import com.Bivago.App.models.HotelModel;

@Repository
public interface HotelRepository extends JpaRepository<HotelModel, Long> {

    public HotelModel findById(UUID id);
    public HotelModel findByHotelName(String hotelName);

    @Query(nativeQuery = true, value = "SELECT HOTELNAME FROM hotel WHERE ID = :hotelUuid")
    public String findHotelNameById(UUID hotelUuid);

    @Query(nativeQuery = true, value = "SELECT * FROM hotel WHERE HOTELCITY LIKE %:hotelCity%")
    public UUID findHotelIdByCity(String hotelCity);

    @Query(nativeQuery = true, value = "SELECT * FROM hotel WHERE HOTELCITY LIKE %:hotelCity%")
    public List<HotelModel> findAllHotelsByCity(String hotelCity);

    // @Query (nativeQuery = true, value = "SELECT * FROM hotel WHERE ID = :uuidByCity AND ID = :uuidByRoomType")
    // public List<HotelModel> findHotelWithIdEqualsId(UUID uuidByCity, UUID uuidByRoomType);

    @Query (nativeQuery = true, value = "SELECT * FROM hotel WHERE ID = :uuidByCity AND ID = :uuidByRoomType")
    public UUID findHotelIdWithIdEqualsId(UUID uuidByCity, UUID uuidByRoomType);

    @Query (nativeQuery = true, value = "SELECT * FROM hotel WHERE ID = :hotelUuid")
    public List<HotelModel> findAllHotelsById(UUID hotelUuid);

    //@Query(nativeQuery = true, value = "SELECT * FROM hotel")
    //public HotelModel findLogin(String userEmail, String userPassword);  
    
}
