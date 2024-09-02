package com.Beevago.App.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Beevago.App.models.HotelModel;

import jakarta.transaction.Transactional;

@Repository
public interface HotelRepository extends JpaRepository<HotelModel, Long> {

    public HotelModel findByHotelName(String hotelName);

    @Query(nativeQuery = true, value = "SELECT * FROM hotel WHERE OWNERID = :userUuid")
    public List<HotelModel> findAllHotelsWithUserId(UUID userUuid);

    @Query(nativeQuery = true, value = "SELECT HOTELNAME FROM hotel WHERE ID = :hotelUuid")
    public String findHotelNameById(UUID hotelUuid);

    @Query(nativeQuery = true, value = "SELECT HOTELADDRESS FROM hotel WHERE ID = :hotelUuid")
    public String findHotelAddressById(UUID hotelUuid);

    @Query(nativeQuery = true, value = "SELECT HOTELCITY FROM hotel WHERE ID = :hotelUuid")
    public String findHotelCityById(UUID hotelUuid);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM hotel h WHERE h.ID = :id")
    public void deleteByHotelId(UUID id);
    
}
