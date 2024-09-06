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

    private static final int HOTELNAMEMINIMUMLENGTH = 2;
    private static final int HOTELNAMEMAXIMUMLENGTH = 48;
    private static final int HOTELADDRESSMINIMUMLENGTH = 3;
    private static final int HOTELADDRESSMAXIMUMLENGTH = 512;
    private static final int HOTELCITYNAMEMINIMUMLENGTH = 3;
    private static final int HOTELCITYNAMEMAXIMUMLENGTH = 128;

    public void saveHotel(HotelModel hotel) throws Exception {        
            
        if (hr.findByHotelName(hotel.getHotelName()) != null) {
            throw new AttributeExistsException("Já existe um Hotel com esse nome.");
        }

        if (hotel.getHotelName().length() < HOTELNAMEMINIMUMLENGTH || hotel.getHotelName().length() > HOTELNAMEMAXIMUMLENGTH ) {
            throw new LengthException("Nome do Hotel deve conter entre " + HOTELNAMEMINIMUMLENGTH + " a "  + HOTELNAMEMAXIMUMLENGTH + " caracteres.");
        }

        if (hotel.getHotelAddress().length() < HOTELADDRESSMINIMUMLENGTH || hotel.getHotelAddress().length() > HOTELADDRESSMAXIMUMLENGTH) {
            throw new LengthException("Endereço do Hotel deve conter entre " + HOTELADDRESSMINIMUMLENGTH + " a " + HOTELADDRESSMAXIMUMLENGTH + " caracteres.");
        }

        if (hotel.getHotelCity().length() < HOTELCITYNAMEMINIMUMLENGTH || hotel.getHotelCity().length() > HOTELCITYNAMEMAXIMUMLENGTH) {
            throw new LengthException("Nome da Cidade do Hotel deve conter entre " + HOTELCITYNAMEMINIMUMLENGTH + " a " + HOTELCITYNAMEMAXIMUMLENGTH + " caracteres.");
        }        

        hr.save(hotel);

    }

    public List<HotelModel> findAllHotels() {
        return hr.findAll();
    } 

    public List<HotelModel> findAllHotelsWithUserId(UUID userId) {
        return hr.findAllHotelsWithUserId(userId);
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
