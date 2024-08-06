/*package com.Bivago.App.models;

import java.io.Serializable;
//import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class AdminHotel implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long code;

    @NotEmpty
    private int hotelID;

    @NotEmpty
    private String hotelAdminName;
    
    @NotEmpty
    @Column(unique = true)
    private String hotelAdminCPF;        
    
    @NotEmpty
    @Column(unique = true)
    private String hotelAdminEmail;
    
    @NotEmpty
    private String hotelAdminPassword;

    // GetterSetters:

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public String getHotelAdminName() {
        return hotelAdminName;
    }

    public void setHotelAdminName(String hotelAdminName) {
        this.hotelAdminName = hotelAdminName;
    }

    public String getHotelAdminCPF() {
        return hotelAdminCPF;
    }

    public void setHotelAdminCPF(String hotelAdminCPF) {
        this.hotelAdminCPF = hotelAdminCPF;
    }

    public String getHotelAdminEmail() {
        return hotelAdminEmail;
    }

    public void setHotelAdminEmail(String hotelAdminEmail) {
        this.hotelAdminEmail = hotelAdminEmail;
    }

    public String getHotelAdminPassword() {
        return hotelAdminPassword;
    }

    public void setHotelAdminPassword(String hotelAdminPassword) {
        this.hotelAdminPassword = hotelAdminPassword;
    }

}*/
