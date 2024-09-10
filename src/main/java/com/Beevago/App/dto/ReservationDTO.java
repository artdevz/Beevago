package com.Beevago.App.dto;

import java.sql.Date;

public record ReservationDTO(Date checkIn, Date checkOut, int qntDePessoas) {}
