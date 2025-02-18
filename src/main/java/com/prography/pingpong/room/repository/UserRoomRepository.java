package com.prography.pingpong.room.repository;

import com.prography.pingpong.room.entity.UserRoom;
import com.prography.pingpong.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoomRepository extends JpaRepository<UserRoom, Integer> {
}
