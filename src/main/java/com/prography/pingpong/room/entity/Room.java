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
    private int id;

    private String title;

//    @ManyToOne(fetch = FetchType.LAZY)
    private int host; // user id

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @Enumerated(EnumType.STRING)
    private RoomStatusType status;

    private Date createdAt = new Date();

    private Date updatedAt = new Date();
}