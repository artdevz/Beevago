package com.Beevago.App.dto;

import java.util.UUID;

import com.Beevago.App.enums.ERoomType;

public record RoomDTO(UUID roomId, ERoomType roomType) {}
