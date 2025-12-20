package io.github.gabrielvavelar.todo.todo.dto;

import java.time.LocalDate;
import java.util.UUID;

public record TodoResponseDto(
        UUID id,
        UUID userId,
        String description,
        LocalDate date,
        Boolean done
) {}
