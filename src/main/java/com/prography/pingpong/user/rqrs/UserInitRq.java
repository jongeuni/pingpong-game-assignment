package com.prography.pingpong.user.rqrs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class UserInitRq {
    private int seed;
    private int quantity;
}
