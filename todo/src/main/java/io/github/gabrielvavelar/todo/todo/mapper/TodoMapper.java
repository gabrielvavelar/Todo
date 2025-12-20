package io.github.gabrielvavelar.todo.todo.mapper;

import io.github.gabrielvavelar.todo.todo.dto.TodoRequestDto;
import io.github.gabrielvavelar.todo.todo.dto.TodoResponseDto;
import io.github.gabrielvavelar.todo.todo.model.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TodoMapper {
    Todo toEntity(TodoRequestDto todoRequest);

    @Mapping(target = "userId", source = "user.id")
    TodoResponseDto toResponse(Todo todo);
}
