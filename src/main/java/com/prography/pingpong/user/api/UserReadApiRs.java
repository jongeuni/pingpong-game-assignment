package com.prography.pingpong.user.api;


import lombok.Getter;

import java.util.List;

@Getter
public class UserReadApiRs {
    private String status;
    private Integer code;
    private String locale;
    private String seed;
    private Integer total;
    private List<UserReadApiData> data;
}