package com.prography.pingpong.common.rs;

public class ApiResponse<T> {
    private Integer code;
    private String message;
    private T result;

    public ApiResponse(Integer code) {
        this.code = code;
        if(code == 200) {
            this.message = "API 요청이 성공했습니다.";
        } else if (code == 201) {
            this.message = "불가능한 요청입니다.";
        } else {
            this.message = "에러가 발생했습니다.";
        }
    }
}