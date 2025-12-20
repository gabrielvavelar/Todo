package io.github.gabrielvavelar.todo.todo.controller;

import io.github.gabrielvavelar.todo.todo.dto.TodoRequestDto;
import io.github.gabrielvavelar.todo.todo.dto.TodoResponseDto;
import io.github.gabrielvavelar.todo.todo.mapper.TodoMapper;
import io.github.gabrielvavelar.todo.todo.model.Todo;
import io.github.gabrielvavelar.todo.todo.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;
    private final TodoMapper todoMapper;

    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody @Valid TodoRequestDto dto) {
        Todo saved = todoService.save(todoMapper.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(todoMapper.toResponse(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable UUID id) {
        todoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponseDto> updateTodo(@PathVariable UUID id, @RequestBody @Valid TodoRequestDto dto) {
        Todo updated = todoService.update(id, todoMapper.toEntity(dto));
        return ResponseEntity.ok(todoMapper.toResponse(updated));
    }

    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> getAllTodos() {
        return ResponseEntity.ok(
                todoService.getAll().stream().map(todoMapper::toResponse).toList()
        );
    }

    @PutMapping("/{id}/done")
    public ResponseEntity<TodoResponseDto> toggleDone(@PathVariable UUID id) {
        Todo updated = todoService.toggleDone(id);
        return ResponseEntity.ok(todoMapper.toResponse(updated));
    }


}
