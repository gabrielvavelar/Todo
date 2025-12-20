package io.github.gabrielvavelar.todo.todo.mapper;

import io.github.gabrielvavelar.todo.todo.dto.TodoRequestDto;
import io.github.gabrielvavelar.todo.todo.dto.TodoResponseDto;
import io.github.gabrielvavelar.todo.todo.model.Todo;
import io.github.gabrielvavelar.todo.user.model.User;
import java.time.LocalDate;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-20T13:47:47-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class TodoMapperImpl implements TodoMapper {

    @Override
    public Todo toEntity(TodoRequestDto todoRequest) {
        if ( todoRequest == null ) {
            return null;
        }

        Todo todo = new Todo();

        todo.setDescription( todoRequest.description() );
        todo.setDate( todoRequest.date() );

        return todo;
    }

    @Override
    public TodoResponseDto toResponse(Todo todo) {
        if ( todo == null ) {
            return null;
        }

        UUID userId = null;
        UUID id = null;
        String description = null;
        LocalDate date = null;
        Boolean done = null;

        userId = todoUserId( todo );
        id = todo.getId();
        description = todo.getDescription();
        date = todo.getDate();
        done = todo.isDone();

        TodoResponseDto todoResponseDto = new TodoResponseDto( id, userId, description, date, done );

        return todoResponseDto;
    }

    private UUID todoUserId(Todo todo) {
        if ( todo == null ) {
            return null;
        }
        User user = todo.getUser();
        if ( user == null ) {
            return null;
        }
        UUID id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
