package com.Beevago.App.enums;

public enum ERole {
    ROLE_USER("USER"),
    // Usuário comum:
    /*
     * - Reservar quartos;
     * 
    */
    
    ROLE_MOD("MOD"),
    // Dono do Hotel:
    /* 
     * - Hospedar hotéis;
     * 
     */

    ROLE_ADMIN("ADMIN");
    // Dono do Site:
    /*     
     * - Acesso ao CRUD;
     * 
     */

    private final String role;
    
    ERole(String role) {
        this.role = role;
    }
 
    public String getRole() {
        return role;
    }
}
