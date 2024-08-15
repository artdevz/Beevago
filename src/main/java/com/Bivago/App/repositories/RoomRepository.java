package com.Bivago.App.repositories;

//import java.util.List;
//import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Bivago.App.models.RoomModel;

@Repository
public interface RoomRepository extends JpaRepository<RoomModel, Long> {
    
    // @Query(nativeQuery = true, value = "")
    // public List<RoomModel> findById();

}
