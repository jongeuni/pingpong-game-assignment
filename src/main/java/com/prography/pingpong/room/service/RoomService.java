package com.prography.pingpong.room.service;

import com.prography.pingpong.common.rs.ApiResponse;
import com.prography.pingpong.common.rs.utile.DateUtil;
import com.prography.pingpong.room.dto.TeamCountDto;
import com.prography.pingpong.room.entity.Room;
import com.prography.pingpong.room.entity.RoomStatusType;
import com.prography.pingpong.room.entity.RoomType;
import com.prography.pingpong.room.entity.UserRoom;
import com.prography.pingpong.room.repository.RoomRepository;
import com.prography.pingpong.room.repository.UserRoomRepository;
import com.prography.pingpong.room.rqrs.RoomCreateRq;
import com.prography.pingpong.room.rqrs.RoomDetailRs;
import com.prography.pingpong.room.rqrs.RoomItemRs;
import com.prography.pingpong.room.rqrs.RoomListRs;
import com.prography.pingpong.user.entity.User;
import com.prography.pingpong.user.entity.UserStatus;
import com.prography.pingpong.user.repository.UserRepository;
import jakarta.transaction.Transactional;
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
    private final AsyncGameService asyncGameService;

    // 방 생성
    public ApiResponse<Void> createRoom(RoomCreateRq rq) {
        Optional<User> user = userRepository.findById(rq.getUserId());

        if (user.isEmpty()) {
            return new ApiResponse<>(201);
        }

        if (isUserNotActive(user.get())) {
            return new ApiResponse<>(201);
        }

        if (userRoomRepository.existsByUserId(rq.getUserId())) {
            return new ApiResponse<>(201);
        }

        Room roomEntity = new Room(rq.getTitle(), rq.getUserId(), rq.getRoomType(), RoomStatusType.WAIT);
        Room room = roomRepository.save(roomEntity);
        UserRoom userRoom = new UserRoom(room.getId(), rq.getUserId(), "RED");
        userRoomRepository.save(userRoom);

        return new ApiResponse<>(200);
    }

    // 방 목록 조회
    public RoomListRs readRoomList(int page, int size) {
        Page<Room> rooms = roomRepository.findAllByOrderByIdAsc(PageRequest.of(page, size));

        List<RoomItemRs> roomItemRs = rooms.getContent().stream().map(room -> new RoomItemRs(room.getId(), room.getTitle(), room.getHost(), room.getRoomType(), room.getStatus())).toList();

        return new RoomListRs(rooms.getTotalElements(), rooms.getTotalPages(), roomItemRs);
    }

    // 방 상세 조회
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
                DateUtil.dateFormat(room.getCreatedAt()),
                DateUtil.dateFormat(room.getUpdatedAt())
        );
        return new ApiResponse<>(200, rs);

    }

    public ApiResponse<Void> joinRoom(int userId, int roomId) {

        Optional<Room> room = roomRepository.findById(roomId);
        Optional<User> user = userRepository.findById(userId);

        // 유저가 비어있을 경우
        if(user.isEmpty()) {
            return new ApiResponse<>(201);
        }

        if (isRoomWaitStatus(room)) {
            return new ApiResponse<>(201);
        }

        if(isUserNotActive(user.get())) {
            return new ApiResponse<>(201);
        }

        // 유저가 현재 참여한 방이 없어야 한다
        if (userRoomRepository.existsByUserId(userId)) {
            return new ApiResponse<>(201);
        }

        int maxUser = getMaxUser(room.get().getRoomType());

        List<UserRoom> userRooms = userRoomRepository.findByRoomId(roomId);

        // 방 정원이 가득차지 않아야 한다
        if(userRooms.size() >= maxUser) {
            return new ApiResponse<>(201);
        }

        String team = getTeam(userRooms);
        UserRoom userRoom = new UserRoom(roomId, userId, team);
        userRoomRepository.save(userRoom);

        return new ApiResponse<>(200);
    }

    private boolean isUserNotActive(User user) {
        return user.getStatus() != UserStatus.ACTIVE;
    }

    private String getTeam(List<UserRoom> userRooms) {
        TeamCountDto teamCount = teamCount(userRooms);

        if (teamCount.getRed() > teamCount.getBlue()) {
            return "BLUE";
        }
        return "RED";
    }

    @Transactional
    public ApiResponse<Void> outRoom(int userId, int roomId) {
        Optional<UserRoom> userRoom = userRoomRepository.findByUserIdAndRoomId(userId, roomId);
        // 현재 방에 참가중인 상태여야 한다
        if (userRoom.isEmpty()) {
            return new ApiResponse<>(201);
        }

        Optional<Room> room= roomRepository.findById(roomId);

        if (isRoomWaitStatus(room)) {
            return new ApiResponse<>(201);
        }

        // 호스트가 나가게 된다면 모든 사람이 나가진다
        if (room.get().getHost() == userId) {
            userRoomRepository.deleteAllByRoomId(roomId);
            room.get().updateStatus(RoomStatusType.FINISH);
        } else {
            userRoomRepository.deleteByUserId(userId);
        }

        return new ApiResponse<>(200);
    }

    public ApiResponse<Void> gameStart(int userId, int roomId) {
        Optional<Room> room= roomRepository.findById(roomId);

        if (isRoomWaitStatus(room)) {
            return new ApiResponse<>(201);
        }
        // 유저는 방의 호스트여야 한다
        if (room.get().getHost() != userId) {
            return new ApiResponse<>(201);
        }

        int maxUser = getMaxUser(room.get().getRoomType());
        // 방 정원이 꽉 차 있어야 한다
        if (!verifyMaxUser(roomId, maxUser)) {
            return new ApiResponse<>(201);
        }

        room.get().updateStatus(RoomStatusType.PROGRESS);
        roomRepository.save(room.get());

        asyncGameService.scheduleRoomFinish(roomId);

        return new ApiResponse<>(200);
    }

    private int getMaxUser(RoomType roomType) {
        return RoomType.SINGLE.equals(roomType) ? 2 : 4;
    }

    private boolean verifyMaxUser(int roomId, int maxUser) {
        List<UserRoom> userRooms = userRoomRepository.findByRoomId(roomId);

        return userRooms.size() >= maxUser;
    }

    @Transactional
    public ApiResponse<Void> teamChange(int userId, int roomId) {
        List<UserRoom> userRooms = userRoomRepository.findByRoomId(roomId);
        Optional<UserRoom> userRoom = userRooms.stream()
                .filter(ur -> ur.getUserId() == userId)
                .findFirst();

        // 유저가 방에 참여한 상태여야 한다
        if(userRoom.isEmpty()) {
            return new ApiResponse<>(201);
        }

        Optional<Room> room= roomRepository.findById(roomId);

        if (isRoomWaitStatus(room)) {
            return new ApiResponse<>(201);
        }

        String changeTeam = userRoom.get().getTeam().equals("RED") ? "BLUE" : "RED";

        TeamCountDto teamCount = teamCount(userRooms);

        RoomType roomType = room.get().getRoomType();
        if(changeTeam.equals("RED") && teamCount.getRed() >= roomType.getMaxUser()/2) {
            return new ApiResponse<>(201);
        }
        if (changeTeam.equals("BLUE") && teamCount.getBlue() >= roomType.getMaxUser()/2) {
            return new ApiResponse<>(201);
        }

        userRoom.get().changeTeam(changeTeam);

        return new ApiResponse<>(200);
    }

    // 방은 대기 상태여야 한다
    private boolean isRoomWaitStatus(Optional<Room> room) {
        return room.isEmpty() || !room.get().getStatus().equals(RoomStatusType.WAIT);
    }

    private TeamCountDto teamCount(List<UserRoom> userRooms) {
        int redCount = 0;
        int blueCount = 0;

        for (UserRoom ur : userRooms) {
            if ("RED".equals(ur.getTeam())) {
                redCount++;
            } else if ("BLUE".equals(ur.getTeam())) {
                blueCount++;
            }
        }
        return new TeamCountDto(redCount, blueCount);
    }

}
