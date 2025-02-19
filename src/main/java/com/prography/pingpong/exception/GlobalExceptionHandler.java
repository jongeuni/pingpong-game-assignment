package com.prography.pingpong.exception;

import com.prography.pingpong.common.rs.ApiResponse;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleAllExceptions(Exception ex) throws Exception {
        return new ApiResponse<>(500);
    }
}
