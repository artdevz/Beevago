package com.Bivago.App.repositories;

//import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;

import com.Bivago.App.models.HotelModel;

public interface HotelRepository extends JpaRepository<HotelModel, Long> {

    //public HotelModel findById(UUID id);
    public HotelModel findByHotelName(String hotelName);

    //@Query(nativeQuery = true, value = "SELECT * FROM hotel")
    //public HotelModel findLogin(String userEmail, String userPassword);  
    
}
