package com.Bivago.App.enums;

public enum ERole {
    ROLE_USER("user"),
    // Usuário comum:
    /*
     * - Reservar quartos;
     * 
    */
    
    ROLE_MOD("mod"),
    // Dono do Hotel:
    /* 
     * - Hospedar hotéis;
     * 
     */

    ROLE_ADMIN("admin");
    // Dono do Site:
    /*
     * - Reservar quartos;
     * - Hospedar hotéis;
     * - Acesso ao CRUD;
     * 
     */

    private String role;
    
    ERole(String role) {
        this.role = role;
    }
 
    public String getRole() {
        return role;
    }
}
