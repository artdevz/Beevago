package com.Bivago.App.enums;

public enum ERoomType {
    ROOMTYPE_LUX("lux"),
    ROOMTYPE_FAMILY("family"),
    ROOMTYPE_STANDARD("standard");

    private String type;
    
    ERoomType(String type) {
        this.type = type;
    }
 
    public String getType() {
        return type;
    }

}
