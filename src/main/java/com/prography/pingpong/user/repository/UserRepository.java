package com.prography.pingpong.user.repository;

import com.prography.pingpong.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
