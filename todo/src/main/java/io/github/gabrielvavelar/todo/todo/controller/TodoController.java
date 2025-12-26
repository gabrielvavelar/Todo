package io.github.gabrielvavelar.todo.todo.controller;

import io.github.gabrielvavelar.todo.security.UserPrincipal;
import io.github.gabrielvavelar.todo.todo.dto.TodoRequestDto;
import io.github.gabrielvavelar.todo.todo.dto.TodoResponseDto;
import io.github.gabrielvavelar.todo.todo.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TodoController {
    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodo(
            @RequestBody @Valid TodoRequestDto dto,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(todoService.save(dto, userPrincipal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        todoService.delete(id, userPrincipal);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponseDto> updateTodo(
            @PathVariable UUID id,
            @RequestBody @Valid TodoRequestDto dto,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        return ResponseEntity.ok(todoService.update(id,dto, userPrincipal));
    }

    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> getAllTodos(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        return ResponseEntity.ok(todoService.getAll(userPrincipal));
    }

    @PutMapping("/{id}/done")
    public ResponseEntity<TodoResponseDto> toggleDone(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        return ResponseEntity.ok(todoService.toggleDone(id, userPrincipal));
    }


}
