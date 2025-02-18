package com.prography.pingpong.room.entity;

import lombok.Getter;

@Getter
public enum RoomType {
    SINGLE(2), DOUBLE(4);
    private final int maxUser;
    RoomType(int maxUser) {
        this.maxUser = maxUser;
    }
}
