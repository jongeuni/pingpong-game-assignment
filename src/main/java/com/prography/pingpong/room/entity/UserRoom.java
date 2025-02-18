package com.prography.pingpong.room.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Table(name = "user_room")
public class UserRoom {
    @Id
    private int id;
    private int roomId;
    private int userId;
    private String team; // blue, red
}