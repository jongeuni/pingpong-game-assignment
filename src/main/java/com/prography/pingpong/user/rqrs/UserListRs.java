package com.prography.pingpong.user.rqrs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class UserListRs {
    private long totalElements;
    private int totalPages;
    private List<UserItemRs> userList;
}
