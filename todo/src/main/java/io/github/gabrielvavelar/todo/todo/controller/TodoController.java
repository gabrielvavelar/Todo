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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable UUID id) {
        todoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponseDto> updateTodo(@PathVariable UUID id, @RequestBody @Valid TodoRequestDto dto) {
        return ResponseEntity.ok(todoService.update(id,dto));
    }

    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> getAllTodos() {
        return ResponseEntity.ok(todoService.getAll());
    }

    @PutMapping("/{id}/done")
    public ResponseEntity<TodoResponseDto> toggleDone(@PathVariable UUID id) {
        return ResponseEntity.ok(todoService.toggleDone(id));
    }


}
