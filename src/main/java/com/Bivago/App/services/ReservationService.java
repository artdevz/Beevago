package com.Bivago.App.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// import com.Bivago.App.enums.ERoomType;
import com.Bivago.App.models.ReservationModel;
import com.Bivago.App.repositories.ReservationRepository;

@Service
public class ReservationService {
    
    @Autowired
    ReservationRepository rr;

    @Autowired
    HotelService hs;

    public void saveReservation(ReservationModel reservation) {
        
        if (reservation.getCheckInDate().after(reservation.getCheckOutDate())) {
            System.out.println("Data Inválida");
        }

        rr.save(reservation);

    }

    public long daysInRoom(Date checkInDate, Date checkOutDate) {
        return ( (checkOutDate.getTime() - checkInDate.getTime() ) / (1000*60*60*24) ) % 365;
    }

    public boolean dateConflicts(UUID roomId, Date checkInDate, Date checkOutDate) {

        List<Date> checkInDates = rr.findAllCheckInDateById(roomId); List<Date> checkOutDates = rr.findAllCheckOutDateById(roomId);

        for (int i = 0; i < checkInDates.size(); i++) {
            if ( 
                checkInDate.before(checkInDates.get(i)) && checkOutDate.after(checkInDates.get(i)) || 
                checkInDate.before(checkOutDates.get(i)) && checkOutDate.after(checkOutDates.get(i)) || 
                checkInDate.after(checkInDates.get(i)) && checkOutDate.before(checkOutDates.get(i)) ) return true;
        }        
        return false;
    }

}
