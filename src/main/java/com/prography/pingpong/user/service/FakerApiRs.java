package com.prography.pingpong.user.service;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class FakerApiRs {
    private String status;
    private Integer code;
    private String locale;
    private String seed;
    private Integer total;
    private List<FakerApiUserRs> data;
}