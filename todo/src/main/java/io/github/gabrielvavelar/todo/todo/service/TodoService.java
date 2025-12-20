package io.github.gabrielvavelar.todo.todo.service;

import io.github.gabrielvavelar.todo.todo.dto.TodoRequestDto;
import io.github.gabrielvavelar.todo.todo.dto.TodoResponseDto;
import io.github.gabrielvavelar.todo.todo.exception.TodoNotFoundException;
import io.github.gabrielvavelar.todo.todo.mapper.TodoMapper;
import io.github.gabrielvavelar.todo.todo.model.Todo;
import io.github.gabrielvavelar.todo.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    public TodoResponseDto save(TodoRequestDto dto) {
        Todo todo = todoMapper.toEntity(dto);
        Todo saved = todoRepository.save(todo);
        return todoMapper.toResponse(saved);
    }

    public void delete(UUID id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found"));

        todoRepository.delete(todo);
    }

    public TodoResponseDto update(UUID id, TodoRequestDto dto) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found"));

        todo.setDescription(dto.description());
        Todo updated = todoRepository.save(todo);
        return todoMapper.toResponse(updated);
    }

    public List<TodoResponseDto> getAll() {
        return todoRepository.findAll()
                .stream()
                .map(todoMapper::toResponse)
                .toList();
    }

    public TodoResponseDto toggleDone(UUID id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found"));

        todo.setDone(!todo.isDone());
        Todo saved = todoRepository.save(todo);
        return todoMapper.toResponse(saved);
    }

}
