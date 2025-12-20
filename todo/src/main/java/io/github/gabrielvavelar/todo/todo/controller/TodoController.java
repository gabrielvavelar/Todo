package io.github.gabrielvavelar.todo.todo.controller;

import io.github.gabrielvavelar.todo.todo.dto.TodoRequestDto;
import io.github.gabrielvavelar.todo.todo.dto.TodoResponseDto;
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

    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody @Valid TodoRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(todoService.save(dto));
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodo(@PathVariable UUID todoId, @RequestBody @Valid TodoRequestDto dto) {
        todoService.delete(todoId, dto.userId());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{todoId}")
    public ResponseEntity<TodoResponseDto> updateTodo(@PathVariable UUID todoId, @RequestBody @Valid TodoRequestDto dto) {
        return ResponseEntity.ok(todoService.update(todoId,dto));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<TodoResponseDto>> getAllTodos(@PathVariable UUID userId) {
        return ResponseEntity.ok(todoService.getAll(userId));
    }

    @PutMapping("/{todoId}/done")
    public ResponseEntity<TodoResponseDto> toggleDone(@PathVariable UUID todoId, @RequestBody @Valid TodoRequestDto dto) {
        return ResponseEntity.ok(todoService.toggleDone(todoId, dto.userId()));
    }


}
