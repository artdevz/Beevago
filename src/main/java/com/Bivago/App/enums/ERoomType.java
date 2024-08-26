package com.Bivago.App.enums;

public enum ERoomType {
    ROOMTYPE_LUX("Luxo"),
    ROOMTYPE_FAMILY("Família"),
    ROOMTYPE_STANDARD("Padrão");

    private final String roomType;
    
    ERoomType(String roomType) {
        this.roomType = roomType;
    }
 
    public String getRoomType() {
        return roomType;
    }

}
