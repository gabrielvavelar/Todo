package io.github.gabrielvavelar.todo.dto;

import jakarta.validation.constraints.NotBlank;

public record TodoRequestDto(
        @NotBlank(message = "Title must not be blank")
        String title,
        @NotBlank(message = "Description must not be blank")
        String description,
        Boolean done
) {}
