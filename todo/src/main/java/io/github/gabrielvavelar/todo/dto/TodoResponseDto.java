package io.github.gabrielvavelar.todo.dto;

import java.util.UUID;

public record TodoResponseDto(
        UUID id,
        String title,
        String description,
        Boolean done
) {}
