package io.github.gabrielvavelar.todo.controller;

import io.github.gabrielvavelar.todo.model.Todo;
import io.github.gabrielvavelar.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PostMapping("/")
    public ResponseEntity<Todo> addTodo(@RequestBody Todo todo) {
        return  ResponseEntity.status(HttpStatus.CREATED).body(todoService.save(todo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable UUID id) {
        todoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable UUID id, @RequestBody Todo todo) {
        return  ResponseEntity.status(HttpStatus.OK).body(todoService.update(id, todo));
    }

    @GetMapping("/")
    public ResponseEntity<List<Todo>> getAllTodos() {
        return ResponseEntity.ok(todoService.getAll());
    }


}
