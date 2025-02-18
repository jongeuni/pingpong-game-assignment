package com.prography.pingpong.room.rqrs;

import com.prography.pingpong.room.entity.RoomStatusType;
import com.prography.pingpong.room.entity.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RoomDetailRs {
    private int id;
    private String title;
    private int hostId;
    private RoomType roomType;
    private RoomStatusType status;
    private String createdAt;
    private String updatedAt;
}
