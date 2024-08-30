package com.Beevago.App.repositories;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Beevago.App.models.ReservationModel;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationModel, Long> {
    
    @Query(nativeQuery = true, value = "SELECT check_in FROM reservation WHERE ROOMID = :roomId")
    List<Date> findAllCheckInDateById(UUID roomId);

    @Query(nativeQuery = true, value = "SELECT check_out FROM reservation WHERE ROOMID = :roomId")
    List<Date> findAllCheckOutDateById(UUID roomId);

}
