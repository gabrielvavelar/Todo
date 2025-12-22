package io.github.gabrielvavelar.todo.user.mapper;

import io.github.gabrielvavelar.todo.user.dto.UserRequestDto;
import io.github.gabrielvavelar.todo.user.dto.UserResponseDto;
import io.github.gabrielvavelar.todo.user.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequestDto userRequestDto);

    UserResponseDto toResponse(User user);
}