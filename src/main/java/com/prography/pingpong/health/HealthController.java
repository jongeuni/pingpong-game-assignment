package com.prography.pingpong.health;

import com.prography.pingpong.common.rs.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/health")
    public ApiResponse<Void> health() {
        return new ApiResponse<>(200);
    }
}
