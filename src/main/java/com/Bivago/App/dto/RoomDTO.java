package com.Bivago.App.dto;

import java.util.UUID;

import com.Bivago.App.enums.ERoomType;

public record RoomDTO(UUID roomId, ERoomType roomType) {}
