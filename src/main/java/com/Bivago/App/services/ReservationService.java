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

    public double reservationPriceCalculator(int qntDePessoas, UUID hotelID, ERoomType roomType, Date checkInDate, Date checkOutDate) {
        
        long daysInRoom = ( (checkOutDate.getTime() - checkInDate.getTime() ) / (1000*60*60*24) ) % 365;
        
        System.out.println(checkInDate.getTime());
        System.out.println(checkOutDate.getTime());

        for (long time = checkInDate.getTime(); time < checkOutDate.getTime(); time+=86400000 ) {
            System.out.printf("Dia: %d\n", time);
            // Verificar se esse .getTime() o Quarto está ocupado.
        }

        switch (roomType) {
            case ROOMTYPE_STANDARD:
                return qntDePessoas * daysInRoom * findPricePerDayForRoomTypeById(hotelID, roomType);
            case ROOMTYPE_FAMILY:
                return qntDePessoas * daysInRoom * findPricePerDayForRoomTypeById(hotelID, roomType);
            case ROOMTYPE_LUX:
                return qntDePessoas * daysInRoom * findPricePerDayForRoomTypeById(hotelID, roomType);
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

    public String roomTypeVal(ERoomType roomType) {
        switch (roomType) {
            case ROOMTYPE_STANDARD: return "valByStandardType"; 
            case ROOMTYPE_FAMILY: return "valByFamilyType"; 
            case ROOMTYPE_LUX: return "valByLuxType"; 
            default: break;
        }
        return null;
    }

}
