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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    
    @ManyToOne
    @JoinColumn(name = "HOTELROOM")    
    private HotelModel hotelRoom;

    @NotNull
    @Column(name = "ROOMNUMBER")
    private int roomNumber;

    @Enumerated                     
    @Column(name = "ROOMTYPE")
    private ERoomType roomType;    

    @NotNull
    @Column(name = "ROOMCAPACITY")
    private int roomCapacity;   

    // Fazer método de calcular preço;
    // Fazer método de verificar disponibilidade;    

    // Getters and Setters:

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public HotelModel getHotelRoom() {
        return hotelRoom;
    }

    public void setHotel(HotelModel hotelRoom) {
        this.hotelRoom = hotelRoom;
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
    
}
