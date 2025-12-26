package io.github.gabrielvavelar.todo.user.controller;

import io.github.gabrielvavelar.todo.user.dto.UserRequestDto;
import io.github.gabrielvavelar.todo.user.dto.UserResponseDto;
import io.github.gabrielvavelar.todo.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.save(dto));
    }
}
