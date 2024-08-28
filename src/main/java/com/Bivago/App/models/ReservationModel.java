package com.Bivago.App.models;

import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

import com.Bivago.App.enums.ERoomType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
// import jakarta.validation.constraints.Size;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="reservation")
public class ReservationModel implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;

    //@NotEmpty
    @Column(name = "hotelID")
    private UUID hotelID;

    //@NotEmpty
    @Column(name = "userID")
    private UUID userID;

    //@NotNull
    @Column(name = "checkIn")
    private Date checkInDate;

    //@NotNull
    @Column(name = "checkOut")
    private Date checkOutDate;

    // @Size(min = 1)
    @Column(name = "pessoas")
    private int quantidadeDePessoas;
    // falta Getters e Setters
    
    //@NotEmpty
    // @Size(min = 0)
    @Column(name = "price")
    private double totalPrice;

    @Enumerated
    @Column(name = "reservationRoomtype")
    private ERoomType roomType;

    // // @NotEmpty
    // @ManyToOne
    // @JoinColumn(name = "userReservation")   
    // private UserModel userReservation;

    // GetterSetters:

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getHotelID() {
        return hotelID;
    }

    public void setHotelID(UUID hotelID) {
        this.hotelID = hotelID;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
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

    // public User getUserReservation() {
    //     return userReservation;
    // }

    // public void setUserReservation(User userReservation) {
    //     this.userReservation = userReservation;
    // }      

}
