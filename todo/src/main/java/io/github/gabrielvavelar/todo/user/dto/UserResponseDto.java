package io.github.gabrielvavelar.todo.user.dto;

import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String username
) {}
