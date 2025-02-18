package com.prography.pingpong.user.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserStatus {
    WAIT("대기"),
    ACTIVE("활성"),
    NON_ACTIVE("비활성");

    private final String name;

}
