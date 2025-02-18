package com.prography.pingpong.room.entity;

import com.prography.pingpong.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

//    @ManyToOne(fetch = FetchType.LAZY)
    private int host; // user id

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @Enumerated(EnumType.STRING)
    private RoomStatusType status;

    private Date createdAt;

    private Date updatedAt;

    public Room(String title, int host, RoomType roomType, RoomStatusType status) {
        this.title = title;
        this.host = host;
        this.roomType = roomType;
        this.status = status;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
}