package io.github.gabrielvavelar.todo.exception;

import java.time.LocalDateTime;

public record ErrorResponseDto (
        int status,
        String message,
        String path,
        LocalDateTime timestamp
){}
