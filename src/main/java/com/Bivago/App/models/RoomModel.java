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

    //@NotEmpty
    @Column(name = "HOTELUUID")
    private UUID hotelUuid;

    @NotEmpty
    @Column(name = "ROOMNUMBER")
    private int roomNumber;

    @NotEmpty
    @Column(name = "ROOMCAPACITY")
    private int roomCapacity;

    @NotEmpty
    @Column(name = "ROOMTYPE")
    private ERoomType roomType;

    // Fazer método de calcular preço;
    // Fazer método de verificar disponibilidade;

}
