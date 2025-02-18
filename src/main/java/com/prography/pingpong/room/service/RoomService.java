package com.prography.pingpong.room.service;

import com.prography.pingpong.common.rs.ApiResponse;
import com.prography.pingpong.room.entity.Room;
import com.prography.pingpong.room.entity.RoomStatusType;
import com.prography.pingpong.room.repository.RoomRepository;
import com.prography.pingpong.room.repository.UserRoomRepository;
import com.prography.pingpong.room.rqrs.RoomCreateRq;
import com.prography.pingpong.room.rqrs.RoomDetailRs;
import com.prography.pingpong.room.rqrs.RoomItemRs;
import com.prography.pingpong.room.rqrs.RoomListRs;
import com.prography.pingpong.user.entity.User;
import com.prography.pingpong.user.entity.UserStatus;
import com.prography.pingpong.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final UserRepository userRepository;
    private final UserRoomRepository userRoomRepository;
    private final RoomRepository roomRepository;

    public ApiResponse<Void> createRoom(RoomCreateRq rq) {
        Optional<User> user = userRepository.findById(rq.getUserId());

        if (user.isEmpty()) {
            return new ApiResponse<>(201);
        }

        if (!user.get().getStatus().equals(UserStatus.ACTIVE)) {
            return new ApiResponse<>(201);
        }

        if (userRoomRepository.existsByUserId(rq.getUserId())) {
            return new ApiResponse<>(201);
        }

        Room room = new Room(rq.getTitle(), rq.getUserId(), rq.getRoomType(), RoomStatusType.WAIT);

        roomRepository.save(room);

        return new ApiResponse<>(200);
    }

    public RoomListRs readRoomList(int page, int size) {
        Page<Room> rooms = roomRepository.findAllByOrderByIdAsc(PageRequest.of(page, size));

        List<RoomItemRs> roomItemRs = rooms.getContent().stream().map(room -> new RoomItemRs(room.getId(), room.getTitle(), room.getHost(), room.getRoomType(), room.getStatus())).toList();

        return new RoomListRs(rooms.getTotalElements(), rooms.getTotalPages(), roomItemRs);
    }

    public ApiResponse<RoomDetailRs> readRoomDetail(int roomId) {
        Optional<Room> roomEntity = roomRepository.findById(roomId);

        if (roomEntity.isEmpty()) {
            return new ApiResponse<>(201);
        }

        Room room = roomEntity.get();
        RoomDetailRs rs = new RoomDetailRs(
                room.getId(),
                room.getTitle(),
                room.getHost(),
                room.getRoomType(),
                room.getStatus(),
                dateFormatChange(room.getCreatedAt()),
                dateFormatChange(room.getUpdatedAt())
        );
        return new ApiResponse<>(200, rs);

    }

    private String dateFormatChange(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
}
