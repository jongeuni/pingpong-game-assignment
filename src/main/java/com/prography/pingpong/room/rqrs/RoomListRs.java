package com.prography.pingpong.room.rqrs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class RoomListRs {
    private long totalElements;
    private int totalPages;
    private List<RoomItemRs> roomList;
}
