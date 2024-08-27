package com.Bivago.App.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Bivago.App.models.ReservationModel;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationModel, Long> {
    
    @Query(nativeQuery = true, value = "SELECT VALBYSTANDARD FROM hotel WHERE ID = :hotelUuid")
    public double findPricePerDayForStandardRoomTypeById(UUID hotelUuid);

    @Query(nativeQuery = true, value = "SELECT VALBYFAMILY FROM hotel WHERE ID = :hotelUuid")
    public double findPricePerDayForFamilyRoomTypeById(UUID hotelUuid);
    
    @Query(nativeQuery = true, value = "SELECT VALBYLUX FROM hotel WHERE ID = :hotelUuid")
    public double findPricePerDayForLuxRoomTypeById(UUID hotelUuid);

}
