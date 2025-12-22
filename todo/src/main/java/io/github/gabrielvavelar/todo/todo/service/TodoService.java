package io.github.gabrielvavelar.todo.todo.service;

import io.github.gabrielvavelar.todo.todo.dto.TodoRequestDto;
import io.github.gabrielvavelar.todo.todo.dto.TodoResponseDto;
import io.github.gabrielvavelar.todo.todo.exception.TodoNotFoundException;
import io.github.gabrielvavelar.todo.todo.mapper.TodoMapper;
import io.github.gabrielvavelar.todo.todo.model.Todo;
import io.github.gabrielvavelar.todo.todo.repository.TodoRepository;
import io.github.gabrielvavelar.todo.user.model.User;
import io.github.gabrielvavelar.todo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;
    private final UserService userService;

    public TodoResponseDto save(TodoRequestDto dto) {
        User user = userService.findById(dto.userId());
        Todo todo = todoMapper.toEntity(dto);
        todo.setUser(user);
        Todo saved = todoRepository.save(todo);
        return todoMapper.toResponse(saved);
    }

    public void delete(UUID todoId, UUID userId) {
        Todo todo = todoRepository.findTodoByIdAndUserId(todoId, userId)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found"));

        todoRepository.delete(todo);
    }

    public TodoResponseDto update(UUID todoId, TodoRequestDto dto) {
        Todo todo = todoRepository.findTodoByIdAndUserId(todoId, dto.userId())
                .orElseThrow(() -> new TodoNotFoundException("Todo not found"));

        todo.setDescription(dto.description());
        Todo updated = todoRepository.save(todo);
        return todoMapper.toResponse(updated);
    }

    public List<TodoResponseDto> getAll(UUID userId) {
        return todoRepository.findByUserId(userId)
                .stream()
                .map(todoMapper::toResponse)
                .toList();
    }

    public TodoResponseDto toggleDone(UUID todoId, UUID userId) {
        Todo todo = todoRepository.findTodoByIdAndUserId(todoId, userId)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found"));

        todo.setDone(!todo.isDone());
        Todo saved = todoRepository.save(todo);
        return todoMapper.toResponse(saved);
    }

}
