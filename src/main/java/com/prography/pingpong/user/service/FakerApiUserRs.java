package com.prography.pingpong.user.service;

import lombok.Getter;
import lombok.Setter;

@Getter
public class FakerApiUserRs {
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