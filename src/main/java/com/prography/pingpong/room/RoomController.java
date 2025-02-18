package com.prography.pingpong.room;

import com.prography.pingpong.common.rs.ApiResponse;
import com.prography.pingpong.room.rqrs.RoomCreateRq;
import com.prography.pingpong.room.rqrs.RoomDetailRs;
import com.prography.pingpong.room.rqrs.RoomListRs;
import com.prography.pingpong.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/room")
    public ApiResponse<Void> createRoom(@RequestBody RoomCreateRq rq) {
        return roomService.createRoom(rq);
    }

    // room list read
    @GetMapping("/room")
    public ApiResponse<RoomListRs> readRoomList(@RequestParam("page") int page, @RequestParam("size") int size) {
        return new ApiResponse<>(200, roomService.readRoomList(page, size));
    }

    @GetMapping("/room/{roomId}")
    // room detail read
    public ApiResponse<RoomDetailRs> readRoomDetail(@PathVariable int roomId) {
        return roomService.readRoomDetail(roomId);
    }

    // room join

    // room out
}
