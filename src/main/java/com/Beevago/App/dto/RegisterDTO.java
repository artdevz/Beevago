package com.Beevago.App.dto;

import java.sql.Date;

import com.Beevago.App.enums.ERole;

public record RegisterDTO(String name, String email, String cpf, Date birthday, String password, String confirmedpassword, ERole role) {}
