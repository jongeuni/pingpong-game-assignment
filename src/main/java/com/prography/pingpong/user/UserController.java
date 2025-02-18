package com.prography.pingpong.user;

import com.prography.pingpong.common.rs.ApiResponse;
import com.prography.pingpong.user.rqrs.UserInitRq;
import com.prography.pingpong.user.rqrs.UserListRs;
import com.prography.pingpong.user.service.UserInitService;
import com.prography.pingpong.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserInitService userInitService;
    private final UserService userService;

    @PostMapping("/init")
    public ApiResponse<Void> init(@RequestBody UserInitRq rq) {

        userInitService.init(rq.getSeed(), rq.getQuantity());
        return new ApiResponse<>(200);
    }

    @GetMapping("/user")
    public ApiResponse<UserListRs> readUserList(@RequestParam("page") int page, @RequestParam("size") int size) {
        return new ApiResponse<>(200, userService.readUserList(page, size));
    }
}
