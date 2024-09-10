package com.Beevago.App.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Beevago.App.exceptions.AttributeExistsException;
import com.Beevago.App.exceptions.LengthException;
import com.Beevago.App.models.HotelModel;
import com.Beevago.App.repositories.HotelRepository;

@Service
public class HotelService {
    
    @Autowired
    private HotelRepository hr;

    private static final int MINIMUMLENGTH = 3;
    private static final int MAXIMUMLENGTH = 48;

    public void saveHotel(HotelModel hotel) throws Exception {        
            
        if (hr.findByHotelName(hotel.getHotelName()) != null) {
            throw new AttributeExistsException("Já existe um Hotel com esse nome.");
        }

        if (hotel.getHotelName().length() < MINIMUMLENGTH || hotel.getHotelName().length() > MAXIMUMLENGTH ) {
            throw new LengthException("Nome do Hotel deve conter entre " + MINIMUMLENGTH + " a "  + MAXIMUMLENGTH + " caracteres.");
        }

        if (hotel.getHotelAddress().length() < MINIMUMLENGTH || hotel.getHotelAddress().length() > MAXIMUMLENGTH) {
            throw new LengthException("Endereço do Hotel deve conter entre " + MINIMUMLENGTH + " a " + MAXIMUMLENGTH + " caracteres.");
        }

        if (hotel.getHotelCity().length() < MINIMUMLENGTH || hotel.getHotelCity().length() > MAXIMUMLENGTH) {
            throw new LengthException("Nome da Cidade do Hotel deve conter entre " + MINIMUMLENGTH + " a " + MAXIMUMLENGTH + " caracteres.");
        }        

        hr.save(hotel);

    }

    public List<HotelModel> findAllHotels() {
        return hr.findAll();
    } 

    public List<HotelModel> findAllHotelsWithUserId(UUID userId) {
        return hr.findAllHotelsWithUserId(userId);
    }
    
    public HotelModel findOwnerById(UUID id) {
        return hr.findOwnerById(id);
    }

    public String findHotelNameById(UUID id) {
        return hr.findHotelNameById(id);
    }

    public String findHotelAddressById(UUID id) {
        return hr.findHotelAddressById(id);
    }

    public String findHotelCityById(UUID id) {
        return hr.findHotelCityById(id);
    }

    public void deleteHotelById(UUID id) {
        hr.deleteByHotelId(id);
    }

}
