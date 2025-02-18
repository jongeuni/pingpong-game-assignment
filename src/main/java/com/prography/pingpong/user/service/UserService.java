package com.prography.pingpong.user.service;

import com.prography.pingpong.user.entity.User;
import com.prography.pingpong.user.repository.UserRepository;
import com.prography.pingpong.user.rqrs.UserItemRs;
import com.prography.pingpong.user.rqrs.UserListRs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserListRs readUserList(int page, int size) {
        Page<User> users = userRepository.findAllByOrderByIdAsc(PageRequest.of(page, size));
        List<UserItemRs> userItemRs = users.map(user -> new UserItemRs(
                user.getId(),
                user.getFakerId(),
                user.getName(),
                user.getEmail(),
                user.getStatus().toString(),
                dateFormatChange(user.getCreatedAt()),
                dateFormatChange(user.getUpdatedAt())
                )).stream().toList();

        return new UserListRs(users.getTotalElements(), users.getTotalPages(), userItemRs);
    }

    private String dateFormatChange(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

}
