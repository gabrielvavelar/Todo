package io.github.gabrielvavelar.todo.todo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.UUID;

public record TodoRequestDto(
        UUID userId,
        @NotBlank(message = "Description must not be blank")
        String description,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date
) {}
