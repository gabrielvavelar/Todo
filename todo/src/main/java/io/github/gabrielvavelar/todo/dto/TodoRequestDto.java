package io.github.gabrielvavelar.todo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record TodoRequestDto(
        @NotBlank(message = "Description must not be blank")
        String description,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date
) {}
