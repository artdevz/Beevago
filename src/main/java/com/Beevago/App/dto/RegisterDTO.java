package com.Beevago.App.dto;

import java.sql.Date;

import com.Beevago.App.enums.ERole;

public record RegisterDTO(String name, String cpf, String login, Date birthday, String password, ERole role) {}