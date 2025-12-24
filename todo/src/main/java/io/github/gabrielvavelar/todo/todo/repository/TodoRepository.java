package io.github.gabrielvavelar.todo.todo.repository;

import io.github.gabrielvavelar.todo.todo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TodoRepository extends JpaRepository<Todo, UUID> {
    List<Todo> findAllByUserId(UUID userId);
    Optional<Todo> findByIdAndUserId(UUID todoId, UUID userId);
}
