package io.github.gabrielvavelar.todo.todo.service;

import io.github.gabrielvavelar.todo.security.UserPrincipal;
import io.github.gabrielvavelar.todo.todo.dto.TodoRequestDto;
import io.github.gabrielvavelar.todo.todo.dto.TodoResponseDto;
import io.github.gabrielvavelar.todo.todo.exception.TodoNotFoundException;
import io.github.gabrielvavelar.todo.todo.mapper.TodoMapper;
import io.github.gabrielvavelar.todo.todo.model.Todo;
import io.github.gabrielvavelar.todo.todo.repository.TodoRepository;
import io.github.gabrielvavelar.todo.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {
    @InjectMocks
    private TodoService todoService;

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private TodoMapper todoMapper;

    @Mock
    private UserPrincipal userPrincipal;

    private User user;
    private UUID todoId;

    @BeforeEach
    void setUp() {
        user = new User("username", "password");
        todoId = UUID.randomUUID();
    }

    @Test
    void shouldSaveTodo() {
        TodoRequestDto dto = new TodoRequestDto(user.getId(),"todo",  LocalDate.of(2025, 1, 1));
        Todo todo = new Todo("todo", LocalDate.of(2025, 1, 1), false, user);
        TodoResponseDto responseDto = new TodoResponseDto(todo.getId(),"todo",  LocalDate.of(2025, 1, 1), false);

        Mockito.when(userPrincipal.getUser()).thenReturn(user);
        Mockito.when(todoMapper.toEntity(dto)).thenReturn(todo);
        Mockito.when(todoRepository.save(todo)).thenReturn(todo);
        Mockito.when(todoMapper.toResponse(todo)).thenReturn(responseDto);

        TodoResponseDto result = todoService.save(dto, userPrincipal);

        assertThat(result).isNotNull();
        Mockito.verify(todoRepository).save(todo);
        assertThat(todo.getUser()).isEqualTo(user);
    }

    @Test
    void shouldDeleteTodo() {
        Todo todo = new Todo("todo", LocalDate.of(2025, 1, 1), false, user);

        Mockito.when(userPrincipal.getUser()).thenReturn(user);
        Mockito.when(todoRepository.findByIdAndUserId(todoId, user.getId()))
                .thenReturn(Optional.of(todo));

        todoService.delete(todoId, userPrincipal);

        Mockito.verify(todoRepository).findByIdAndUserId(todoId, user.getId());
        Mockito.verify(todoRepository).delete(todo);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingTodo() {
        Mockito.when(userPrincipal.getUser()).thenReturn(user);
        Mockito.when(todoRepository.findByIdAndUserId(todoId, user.getId()))
                .thenReturn(Optional.empty());

        var error = catchThrowable(() -> todoService.delete(todoId, userPrincipal));
        assertThat(error).isInstanceOf(TodoNotFoundException.class);

        Mockito.verify(todoRepository, Mockito.never()).delete(Mockito.any());
    }

    @Test
    void shouldUpdateTodo() {
        TodoRequestDto dto =
                new TodoRequestDto(user.getId(), "updated todo", LocalDate.of(2025, 1, 1));

        Todo todo = new Todo("old todo", LocalDate.of(2025, 1, 1), false, user);
        TodoResponseDto responseDto =
                new TodoResponseDto(todo.getId(), "updated todo", LocalDate.of(2025, 1, 1), false);

        Mockito.when(userPrincipal.getUser()).thenReturn(user);
        Mockito.when(todoRepository.findByIdAndUserId(todoId, user.getId()))
                .thenReturn(Optional.of(todo));
        Mockito.when(todoRepository.save(todo)).thenReturn(todo);
        Mockito.when(todoMapper.toResponse(todo)).thenReturn(responseDto);

        TodoResponseDto result = todoService.update(todoId, dto, userPrincipal);

        assertThat(result).isEqualTo(responseDto);
        assertThat(todo.getDescription()).isEqualTo("updated todo");

        Mockito.verify(todoRepository).findByIdAndUserId(todoId, user.getId());
        Mockito.verify(todoRepository).save(todo);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingTodo() {
        TodoRequestDto dto =
                new TodoRequestDto(user.getId(), "updated todo", LocalDate.of(2025, 1, 1));

        Mockito.when(userPrincipal.getUser()).thenReturn(user);
        Mockito.when(todoRepository.findByIdAndUserId(todoId, user.getId()))
                .thenReturn(Optional.empty());

        var error = catchThrowable(() -> todoService.update(todoId, dto, userPrincipal));
        assertThat(error).isInstanceOf(TodoNotFoundException.class);

        Mockito.verify(todoRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void shouldReturnAllTodosFromUser() {
        Todo todo1 = new Todo("todo 1", LocalDate.of(2025, 1, 1), false, user);
        Todo todo2 = new Todo("todo 2", LocalDate.of(2025, 2, 1), false, user);

        TodoResponseDto response1 =
                new TodoResponseDto(todo1.getId(), "todo 1", LocalDate.of(2025, 1, 1), false);
        TodoResponseDto response2 =
                new TodoResponseDto(todo2.getId(), "todo 2", LocalDate.of(2025, 2, 1), false);

        Mockito.when(userPrincipal.getUser()).thenReturn(user);
        Mockito.when(todoRepository.findAllByUserId(user.getId()))
                .thenReturn(List.of(todo1, todo2));
        Mockito.when(todoMapper.toResponse(todo1)).thenReturn(response1);
        Mockito.when(todoMapper.toResponse(todo2)).thenReturn(response2);

        List<TodoResponseDto> result = todoService.getAll(userPrincipal);

        assertEquals(2, result.size());
        assertEquals(List.of(response1, response2), result);

        Mockito.verify(todoRepository).findAllByUserId(user.getId());
    }

    @Test
    void shouldReturnEmptyListWhenUserHasNoTodos() {
        Mockito.when(userPrincipal.getUser()).thenReturn(user);
        Mockito.when(todoRepository.findAllByUserId(user.getId()))
                .thenReturn(List.of());

        List<TodoResponseDto> result = todoService.getAll(userPrincipal);

        assertTrue(result.isEmpty());
        Mockito.verify(todoRepository).findAllByUserId(user.getId());
    }

    @Test
    void shouldToggleTodoDone() {
        Todo todo = new Todo("todo", LocalDate.of(2025, 1, 1), false, user);
        todo.setDone(false);

        TodoResponseDto responseDto =
                new TodoResponseDto(todo.getId(), "todo", LocalDate.of(2025, 1, 1), true);

        Mockito.when(userPrincipal.getUser()).thenReturn(user);
        Mockito.when(todoRepository.findByIdAndUserId(todoId, user.getId()))
                .thenReturn(Optional.of(todo));
        Mockito.when(todoRepository.save(todo)).thenReturn(todo);
        Mockito.when(todoMapper.toResponse(todo)).thenReturn(responseDto);

        TodoResponseDto result = todoService.toggleDone(todoId, userPrincipal);

        assertThat(todo.isDone()).isTrue();
        assertThat(result).isEqualTo(responseDto);

        Mockito.verify(todoRepository).findByIdAndUserId(todoId, user.getId());
        Mockito.verify(todoRepository).save(todo);
    }

    @Test
    void shouldThrowExceptionWhenTogglingNonExistingTodo() {
        Mockito.when(userPrincipal.getUser()).thenReturn(user);
        Mockito.when(todoRepository.findByIdAndUserId(todoId, user.getId()))
                .thenReturn(Optional.empty());

        var error = catchThrowable(() -> todoService.toggleDone(todoId, userPrincipal));
        assertThat(error).isInstanceOf(TodoNotFoundException.class);

        Mockito.verify(todoRepository, Mockito.never()).save(Mockito.any());
    }
}
