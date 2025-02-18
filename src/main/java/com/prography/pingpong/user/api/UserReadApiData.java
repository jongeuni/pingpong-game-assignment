package com.prography.pingpong.user.api;

import lombok.Getter;

@Getter
public class UserReadApiData {
    private Integer id;
    private String uuid;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private String ip;
    private String macAddress;
    private String website;
    private String image;
}