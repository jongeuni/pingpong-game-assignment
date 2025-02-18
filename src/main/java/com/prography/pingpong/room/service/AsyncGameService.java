package com.prography.pingpong.room.service;

import com.prography.pingpong.room.entity.Room;
import com.prography.pingpong.room.entity.RoomStatusType;
import com.prography.pingpong.room.repository.RoomRepository;
import com.prography.pingpong.room.repository.UserRoomRepository;
import com.prography.pingpong.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AsyncGameService {
    private final UserRepository userRepository;
    private final UserRoomRepository userRoomRepository;
    private final RoomRepository roomRepository;

    @Async
    @Transactional
    public void scheduleRoomFinish(int roomId) {
        try {
            Thread.sleep(60000);
            Room room = roomRepository.findById(roomId).orElseThrow();
            if (room.getStatus() == RoomStatusType.PROGRESS) {
                room.updateStatus(RoomStatusType.FINISH);
                userRoomRepository.deleteAllByRoomId(room.getId());
                roomRepository.save(room);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
