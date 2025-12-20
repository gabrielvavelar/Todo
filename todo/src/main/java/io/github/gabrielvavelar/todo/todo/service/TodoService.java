package io.github.gabrielvavelar.todo.todo.service;

import io.github.gabrielvavelar.todo.todo.exception.TodoNotFoundException;
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

    public Todo save(Todo todo) {
        return todoRepository.save(todo);
    }

    public void delete(UUID id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found"));

        todoRepository.delete(todo);
    }

    public Todo update(UUID id, Todo updatedTodo) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found"));

        todo.setDescription(updatedTodo.getDescription());

        return todoRepository.save(todo);
    }

    public List<Todo> getAll() {
        return todoRepository.findAll();
    }

    public Todo toggleDone(UUID id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found"));

        todo.setDone(!todo.isDone());

        return todoRepository.save(todo);
    }

}
