package com.prography.pingpong.user;

import com.prography.pingpong.common.rs.ApiResponse;
import com.prography.pingpong.user.rqrs.UserInitRq;
import com.prography.pingpong.user.rqrs.UserListRs;
import com.prography.pingpong.user.service.UserInitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserInitService userInitService;

    @PostMapping("/init")
    public ApiResponse<Void> init(@RequestBody UserInitRq rq) {

        userInitService.init(rq.getSeed(), rq.getQuantity());
        return new ApiResponse<>(200);
    }

    // user all read controller
    @GetMapping("/user")
    public ApiResponse<UserListRs> readUserList(@RequestParam("page") int page, @RequestParam("size") int size) {

        return new ApiResponse<>(200);
    }
}
