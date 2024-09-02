package com.Beevago.App.models;

import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

import com.Beevago.App.enums.ERoomType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="reservation")
public class ReservationModel implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;

    //@NotEmpty
    @Column(name = "userID")
    private UUID userId;

    //@NotEmpty
    @Column(name = "hotelID")
    private UUID hotelId;

    @Column(name = "roomID")
    private UUID roomId;

    //@NotNull
    @Column(name = "checkIn")
    private Date checkInDate;

    //@NotNull
    @Column(name = "checkOut")
    private Date checkOutDate;

    // @Size(min = 1)
    @Column(name = "pessoas")
    private int quantidadeDePessoas;
    
    //@NotEmpty
    // @Size(min = 0)
    @Column(name = "price")
    private double totalPrice;

    @Enumerated
    @Column(name = "reservationRoomtype")
    private ERoomType roomType;

    // GetterSetters:

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getHotelId() {
        return hotelId;
    }

    public void setHotelId(UUID hotelId) {
        this.hotelId = hotelId;
    }

    public UUID getRoomId() {
        return roomId;
    }

    public void setRoomId(UUID roomId) {
        this.roomId = roomId;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getQuantidadeDePessoas() {
        return quantidadeDePessoas;
    }

    public void setQuantidadeDePessoas(int quantidadeDePessoas) {
        this.quantidadeDePessoas = quantidadeDePessoas;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ERoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(ERoomType roomType) {
        this.roomType = roomType;
    }

}
