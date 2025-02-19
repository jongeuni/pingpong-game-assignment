package com.prography.pingpong.room;

import com.prography.pingpong.common.rs.ApiResponse;
import com.prography.pingpong.room.entity.Room;
import com.prography.pingpong.room.entity.RoomStatusType;
import com.prography.pingpong.room.entity.RoomType;
import com.prography.pingpong.room.entity.UserRoom;
import com.prography.pingpong.room.repository.RoomRepository;
import com.prography.pingpong.room.repository.UserRoomRepository;
import com.prography.pingpong.room.service.AsyncGameService;
import com.prography.pingpong.room.service.RoomService;
import com.prography.pingpong.user.entity.User;
import com.prography.pingpong.user.entity.UserStatus;
import com.prography.pingpong.user.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.doAnswer;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GameStartTest {

    @Autowired
    private RoomService gameService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoomRepository userRoomRepository;

    @MockitoBean
    private AsyncGameService asyncGameService;

    private User hostUser;
    private User gestUser;
    private Room room;

    @Before
    public void setUp() {
        // 1. 유저 두 명 삽입
        hostUser = userRepository.save(new User(1, 1, "name", "email", UserStatus.ACTIVE, new Date(), new Date()));
        gestUser = userRepository.save(new User(2, 2, "name", "email", UserStatus.ACTIVE, new Date(), new Date()));

        // 2. 방 생성 (호스트는 첫 번째 유저)
        room = roomRepository.save(new Room("title", hostUser.getId(), RoomType.SINGLE, RoomStatusType.WAIT));

        userRoomRepository.save(new UserRoom(room.getId(), hostUser.getId(), "RED"));
        userRoomRepository.save(new UserRoom(room.getId(), gestUser.getId(), "BLUE"));
    }


    @Test
    public void testGameStartRoomStatusChange() throws InterruptedException {
        // given
        doAnswer(invocation -> {
            Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                Optional<Room> updatedRoomOpt = roomRepository.findById(room.getId());
                if (updatedRoomOpt.isPresent()) {
                    Room updatedRoom = updatedRoomOpt.get();
                    updatedRoom.updateStatus(RoomStatusType.FINISH);
                    roomRepository.save(updatedRoom);
                }
            }, 60, TimeUnit.SECONDS);
            return null;
        }).when(asyncGameService).scheduleRoomFinish(room.getId());

        // when
        ApiResponse<Void> response = gameService.gameStart(hostUser.getId(), room.getId());

        // then
        assertEquals(200, response.getCode()); // 게임 시작 성공

        // 60초 대기 (비동기 작업 실행을 기다림)
        Thread.sleep(61000); // 60초 + 1초의 여유

        // 방 상태가 FINISH로 변경되었는지 확인
        Room updatedRoom = roomRepository.findById(room.getId()).get();
        assertEquals(RoomStatusType.FINISH, updatedRoom.getStatus());
    }
}
