package io.github.gabrielvavelar.todo.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UserRequestDto(
        @NotBlank(message = "Username must not be blank")
        String username,
        @NotBlank(message = "Password must not be blank")
        String password
)
{}
