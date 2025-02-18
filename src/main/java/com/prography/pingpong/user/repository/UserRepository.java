package com.prography.pingpong.user.repository;

import com.prography.pingpong.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Member;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    Page<User> findAllByOrderByIdAsc(Pageable pageable);
}
