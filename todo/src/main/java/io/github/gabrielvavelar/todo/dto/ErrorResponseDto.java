package io.github.gabrielvavelar.todo.dto;

import java.time.LocalDateTime;

public record ErrorResponseDto (
        int status,
        String message,
        String path,
        LocalDateTime timestamp
){}
