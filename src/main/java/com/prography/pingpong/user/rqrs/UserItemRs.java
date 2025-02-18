package com.prography.pingpong.user.rqrs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class UserItemRs {
    private int id;
    private int fakerId;
    private String name;
    private String email;
    private String status;
    private String createdAt;
    private String updatedAt;
}
