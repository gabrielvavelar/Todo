package io.github.gabrielvavelar.todo.mapper;

import io.github.gabrielvavelar.todo.dto.TodoRequestDto;
import io.github.gabrielvavelar.todo.dto.TodoResponseDto;
import io.github.gabrielvavelar.todo.model.Todo;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-18T10:50:29-0300",
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

        todo.setTitle( todoRequest.title() );
        todo.setDescription( todoRequest.description() );
        if ( todoRequest.done() != null ) {
            todo.setDone( todoRequest.done() );
        }

        return todo;
    }

    @Override
    public TodoResponseDto toResponse(Todo todo) {
        if ( todo == null ) {
            return null;
        }

        UUID id = null;
        String title = null;
        String description = null;
        Boolean done = null;

        id = todo.getId();
        title = todo.getTitle();
        description = todo.getDescription();
        done = todo.isDone();

        TodoResponseDto todoResponseDto = new TodoResponseDto( id, title, description, done );

        return todoResponseDto;
    }
}
