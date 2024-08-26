package com.Bivago.App.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Bivago.App.enums.ERoomType;
import com.Bivago.App.models.ReservationModel;
import com.Bivago.App.repositories.ReservationRepository;

@Service
public class ReservationService {
    
    @Autowired
    ReservationRepository rr;

    public void saveReservation(ReservationModel reservation) {
        
        if (reservation.getCheckInDate().after(reservation.getCheckOutDate())) {
            System.out.println("Data Inválida");
        }

        // rr.save(reservation);

    }

    public double reservationPriceCalculator(int qntDePessoas, ERoomType roomType) {
        
        switch (roomType) {
            case ROOMTYPE_STANDARD:
                return qntDePessoas * 100;
            case ROOMTYPE_FAMILY:
                return qntDePessoas * 150;
            case ROOMTYPE_LUX:
                return qntDePessoas * 800;
            default:
                break;
        }

        return qntDePessoas * 350800.47;
    }

}
