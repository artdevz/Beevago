package com.Bivago.App.models;

import java.io.Serializable;
import java.util.UUID;

import com.Bivago.App.enums.ERoomType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name="room")
public class RoomModel implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;

    @NotEmpty
    @Column(name = "HOTELUUID")
    private UUID hotelUuid;

    @NotEmpty
    @Column(name = "ROOMNUMBER")
    private int roomNumber;

    @NotEmpty
    @Column(name = "ROOMTYPE")
    private ERoomType roomType;

    @NotEmpty
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

    public UUID getHotelUuid() {
        return hotelUuid;
    }

    public void setHotelUuid(UUID hotelUuid) {
        this.hotelUuid = hotelUuid;
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
