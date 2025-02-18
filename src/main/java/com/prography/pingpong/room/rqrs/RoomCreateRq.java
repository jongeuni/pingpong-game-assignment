package com.prography.pingpong.room.rqrs;

import com.prography.pingpong.room.entity.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RoomCreateRq {
    private int userId;
    private RoomType roomType;
    private String title;
}