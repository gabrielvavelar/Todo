package io.github.gabrielvavelar.todo.mapper;

import io.github.gabrielvavelar.todo.dto.TodoRequestDto;
import io.github.gabrielvavelar.todo.dto.TodoResponseDto;
import io.github.gabrielvavelar.todo.model.Todo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoMapper {
    Todo toEntity(TodoRequestDto todoRequest);

    TodoResponseDto toResponse(Todo todo);
}
