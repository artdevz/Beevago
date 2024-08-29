package com.Bivago.App.services;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Bivago.App.enums.ERoomType;
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

    public boolean isRoomBusyInTheDays(Date checkInDate, Date checkOutDate) {
        
        long daysInRoom = daysInRoom(checkInDate, checkOutDate);
        
        long[] busyDays = new long[(int)daysInRoom]; int count = 0;
        for (long time = checkInDate.getTime(); time < checkOutDate.getTime(); time+=86400000 ) {
            System.out.println(busyDays[count++]);
        }

        return false;
    }

    public double reservationPriceCalculator(int qntDePessoas, double pricePerDay, Date checkInDate, Date checkOutDate) {
        
        long daysInRoom = daysInRoom(checkInDate, checkOutDate);
        
        System.out.println("CheckIn:" + checkInDate.getTime());
        System.out.println("CheckOut: " + checkOutDate.getTime());

        long[] busyDays = new long[(int)daysInRoom]; int count = 0;
        for (long time = checkInDate.getTime(); time < checkOutDate.getTime(); time+=86400000 ) busyDays[count++] = time;

        // switch (roomType) {
        //     case ROOMTYPE_STANDARD:
        //         return qntDePessoas * daysInRoom * findPricePerDayForRoomTypeById(hotelID, roomType);
        //     case ROOMTYPE_FAMILY:
        //         return qntDePessoas * daysInRoom * findPricePerDayForRoomTypeById(hotelID, roomType);
        //     case ROOMTYPE_LUX:
        //         return qntDePessoas * daysInRoom * findPricePerDayForRoomTypeById(hotelID, roomType);
        //     default:
        //         break;
        // }
        
        return qntDePessoas * pricePerDay * daysInRoom;
    }

    // public double findPricePerDayForRoomTypeById(UUID id, ERoomType roomType) {
    //     switch (roomType) {
    //         case ROOMTYPE_STANDARD: return rr.findPricePerDayForStandardRoomTypeById(id);                
    //         case ROOMTYPE_FAMILY: return rr.findPricePerDayForFamilyRoomTypeById(id);                
    //         case ROOMTYPE_LUX: return rr.findPricePerDayForLuxRoomTypeById(id);                
    //         default: break;
    //     }
    //     return 350800.47;
    // }

}
