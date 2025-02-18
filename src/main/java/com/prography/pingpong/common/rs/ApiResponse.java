package com.prography.pingpong.common.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private Integer code;
    private String message;
    private T result;

    public ApiResponse(Integer code) {
        this.code = code;
        setMessage(code);
    }

    public ApiResponse(Integer code, T result) {
        this.code = code;
        setMessage(code);
        this.result = result;
    }

    private void setMessage(int code) {
        if(code == 200) {
            this.message = "API 요청이 성공했습니다.";
        } else if (code == 201) {
            this.message = "불가능한 요청입니다.";
        } else {
            this.message = "에러가 발생했습니다.";
        }
    }
}