package com.Bivago.App.models;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

//import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="hotel")
public class HotelModel implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;

    @NotEmpty
    @Size(min = 2, max = 48, message = "Nome do Hotel deve conter entre 2 a 48 caracteres.")
    @Column(name = "HOTELNAME", unique = true)
    private String hotelName;

    @NotEmpty
    @Size(max = 512, message = "Endereço do Hotel deve conter até no máximo 512 caracteres.")
    @Column(name = "HOTELADDRESS")
    private String hotelAddress;

    @NotEmpty
    @Size(max = 128, message = "Nome da Cidade deve conter até no máximo 128 caracteres.")
    @Column(name = "HOTELCITY")
    private String hotelCity;
    
    @OneToMany(mappedBy = "hotelRoom", fetch = FetchType.LAZY)
    private List<RoomModel> hotelRooms;

    // Construtores:
    public HotelModel() {}

    public HotelModel(String hotelName) {
        this.hotelName = hotelName;
    }

    // GetterSetters:

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelAddress() {
        return hotelAddress;
    }

    public void setHotelAddress(String hotelAddress) {
        this.hotelAddress = hotelAddress;
    }

    public String getHotelCity() {
        return hotelCity;
    }

    public void setHotelCity(String hotelCity) {
        this.hotelCity = hotelCity;
    }

    public List<RoomModel> getRooms() {
        return hotelRooms;
    }

    public void setRooms(List<RoomModel> hotelRooms) {
        this.hotelRooms = hotelRooms;
    }    

}
