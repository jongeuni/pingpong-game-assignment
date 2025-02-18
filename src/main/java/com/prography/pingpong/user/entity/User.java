package com.prography.pingpong.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity()
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    private int id;

    private int fakerId;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private Date createdAt;

    private Date updatedAt;
}
