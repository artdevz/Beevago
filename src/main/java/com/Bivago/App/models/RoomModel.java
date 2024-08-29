package com.Bivago.App.models;

import java.io.Serializable;
import java.util.UUID;

import com.Bivago.App.enums.ERoomType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="room")
public class RoomModel implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "HOTELID")
    private UUID hotelId;

    @NotNull
    @Column(name = "ROOMNUMBER")
    private int roomNumber;

    @Enumerated                     
    @Column(name = "ROOMTYPE")
    private ERoomType roomType;    

    @NotNull
    @Column(name = "ROOMCAPACITY")
    private int roomCapacity;
    
    @Column(name = "ROOMPRICE")
    private int roomPrice;

    private String roomHotelName;
    private String roomHotelAddress;
    private String roomHotelCity;
    
    public String getRoomHotelName() {
        return roomHotelName;
    }

    public void setRoomHotelName(String roomHotelName) {
        this.roomHotelName = roomHotelName;
    }

    public String getRoomHotelAddress() {
        return roomHotelAddress;
    }

    public void setRoomHotelAddress(String roomHotelAddress) {
        this.roomHotelAddress = roomHotelAddress;
    }

    public String getRoomHotelCity() {
        return roomHotelCity;
    }

    public void setRoomHotelCity(String roomHotelCity) {
        this.roomHotelCity = roomHotelCity;
    }

    // Getters and Setters:
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }   

    public UUID getHotelId() {
        return hotelId;
    }

    public void setHotelId(UUID hotelId) {
        this.hotelId = hotelId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public ERoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(ERoomType roomType) {
        this.roomType = roomType;
    }

    public int getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity = roomCapacity;
    }

    public int getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(int roomPrice) {
        this.roomPrice = roomPrice;
    }

}
