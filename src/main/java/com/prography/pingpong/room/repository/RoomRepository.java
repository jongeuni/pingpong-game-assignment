package com.prography.pingpong.room.repository;

import com.prography.pingpong.room.entity.Room;
import com.prography.pingpong.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    Page<Room> findAllByOrderByIdAsc(Pageable pageable);
}
