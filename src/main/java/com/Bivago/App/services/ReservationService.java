package com.Bivago.App.services;

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

    public double reservationPriceCalculator(int qntDePessoas, UUID hotelID, ERoomType roomType) {
        
        switch (roomType) {
            case ROOMTYPE_STANDARD:
                return qntDePessoas * findPricePerDayForRoomTypeById(hotelID, roomType);
            case ROOMTYPE_FAMILY:
                return qntDePessoas * findPricePerDayForRoomTypeById(hotelID, roomType);
            case ROOMTYPE_LUX:
                return qntDePessoas * findPricePerDayForRoomTypeById(hotelID, roomType);
            default:
                break;
        }

        return qntDePessoas * 350800.47;
    }

    public double findPricePerDayForRoomTypeById(UUID id, ERoomType roomType) {
        switch (roomType) {
            case ROOMTYPE_STANDARD: return rr.findPricePerDayForStandardRoomTypeById(id);                
            case ROOMTYPE_FAMILY: return rr.findPricePerDayForFamilyRoomTypeById(id);                
            case ROOMTYPE_LUX: return rr.findPricePerDayForLuxRoomTypeById(id);                
            default: break;
        }
        return 350800.47;
    }

}
