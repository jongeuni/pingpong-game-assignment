package com.prography.pingpong.room.repository;

import com.prography.pingpong.room.entity.UserRoom;
import com.prography.pingpong.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRoomRepository extends JpaRepository<UserRoom, Integer> {
    boolean existsByUserId(int userId);
    List<UserRoom> findByRoomId(int roomId);
    Optional<UserRoom> findByUserIdAndRoomId(int userId, int roomId);
    void deleteAllByRoomId(int roomId);
    void deleteByUserId(int userId);
}
