package com.prography.pingpong.room.entity;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int roomId;
    private int userId;
    private String team; // blue, red
    public UserRoom(int roomId, int userId, String team) {
        this.roomId = roomId;
        this.userId = userId;
        this.team = team;
    }

    public void changeTeam(String team) {
        this.team = team;
    }
}