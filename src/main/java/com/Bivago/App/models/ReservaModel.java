package com.Bivago.App.models;

import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="reservation")
public class ReservaModel implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;

    //@NotEmpty
    @Column(name = "hotelID")
    private int hotelID;

    //@NotEmpty
    @Column(name = "userID")
    private int userID;

    //@NotNull
    @Column(name = "checkIn")
    private Date checkInDate;

    //@NotNull
    @Column(name = "checkOut")
    private Date checkOutDate;

    @Column(name = "pessoas")
    private int quantidadeDePessoas;
    // falta Getters e Setters
    
    //@NotEmpty
    @Column(name = "price")
    private double totalPrice;

    // @NotEmpty
    @ManyToOne
    @JoinColumn(name = "userReservation")   
    private UserModel userReservation;

    // GetterSetters:

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
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

    // public User getUserReservation() {
    //     return userReservation;
    // }

    // public void setUserReservation(User userReservation) {
    //     this.userReservation = userReservation;
    // }      

}
