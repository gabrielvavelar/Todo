package io.github.gabrielvavelar.todo.user.service;

import io.github.gabrielvavelar.todo.user.dto.UserRequestDto;
import io.github.gabrielvavelar.todo.user.dto.UserResponseDto;
import io.github.gabrielvavelar.todo.user.mapper.UserMapper;
import io.github.gabrielvavelar.todo.user.model.User;
import io.github.gabrielvavelar.todo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserResponseDto save(UserRequestDto dto) {
        if(userRepository.existsByUsername(dto.username()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");

        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User saved = userRepository.save(user);
        return userMapper.toResponse(saved);
    }
}
