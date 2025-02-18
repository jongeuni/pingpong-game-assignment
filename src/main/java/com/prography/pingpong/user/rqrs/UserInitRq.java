package com.prography.pingpong.user.rqrs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserInitRq {
    private int seed;
    private int quantity;
}
