package io.github.gabrielvavelar.todo.dto;

public record TodoRequestDto(
        String title,
        String description,
        Boolean done
) {}
