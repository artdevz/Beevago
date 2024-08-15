package com.Bivago.App.repositories;

//import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Bivago.App.models.RoomModel;

@Repository
public interface RoomRepository extends JpaRepository<RoomModel, Long> {
    // BUG no UUID do Hotel
    @Query(nativeQuery = true, value = "SELECT * FROM room WHERE HOTELUUID = :hotelUuid AND ROOMNUMBER = :roomNumber")
    public RoomModel findByRoomNumber(UUID hoteluuid, int roomNumber);

    // @Query(nativeQuery = true, value = "")
    // public List<RoomModel> findById();

}
