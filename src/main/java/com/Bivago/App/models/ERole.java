package com.Bivago.App.models;

public enum ERole {
    ROLE_USER,
    // Usuário comum:
    /*
     * - Reservar quartos;
     * 
    */
    
    ROLE_MOD,
    // Dono do Hotel:
    /* 
     * - Hospedar hotéis;
     * 
     */

    ROLE_ADMIN
    // Dono do Site:
    /*
     * - Reservar quartos;
     * - Hospedar hotéis;
     * - Acesso ao CRUD;
     * 
     */
}
