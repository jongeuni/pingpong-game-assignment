package com.prography.pingpong.user.service;

import com.prography.pingpong.room.repository.RoomRepository;
import com.prography.pingpong.room.repository.UserRoomRepository;
import com.prography.pingpong.user.entity.User;
import com.prography.pingpong.user.entity.UserStatus;
import com.prography.pingpong.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserInitService {
    private final FakerApiClient fakerApiClient;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final UserRoomRepository userRoomRepository;

    @Transactional
    public void init(int seed, int quantity) {
        deleteAll();

        FakerApiRs apiRs = fakerApiClient.fetchUsers(seed, quantity, "ko_KR");

        List<User> users = apiRs.getData().stream().map(user -> User.builder()
                .id(user.getId())
                .fakerId(user.getId())
                .name(user.getUsername())
                .email(user.getEmail())
                .status(getStatus(user.getId()))
                .createdAt(new Date())
                .updatedAt(new Date()).build())
                .sorted(Comparator.comparing(User::getId)).toList();

        userRepository.saveAll(users);
    }

    private void deleteAll() {
        userRepository.deleteAll();
        roomRepository.deleteAll();
        userRoomRepository.deleteAll();
    }

    private UserStatus getStatus(int id) {
        if(id <= 30 ) { // id가 30 이하
            return UserStatus.ACTIVE;
        } else if (id <= 60) { // id가 60 이하
            return UserStatus.WAIT;
        } else { // id가 61 이상
            return UserStatus.NON_ACTIVE;
        }
    }
}
