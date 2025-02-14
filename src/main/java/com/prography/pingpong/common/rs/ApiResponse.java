package com.prography.pingpong.common.rs;

public class ApiResponse<T> {
    private Integer code;
    private String message;
    private T result;
}