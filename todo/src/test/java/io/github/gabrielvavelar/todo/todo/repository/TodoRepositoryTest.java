package io.github.gabrielvavelar.todo.todo.repository;

import io.github.gabrielvavelar.todo.todo.model.Todo;
import io.github.gabrielvavelar.todo.user.model.User;
import io.github.gabrielvavelar.todo.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class TodoRepositoryTest {
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("username", "password");
        userRepository.save(user);

        List<Todo> todos = List.of(
                new Todo("todo_1",  LocalDate.of(2025, 1, 1), false, user),
                new Todo("todo_2",  LocalDate.of(2025, 1, 1), false, user),
                new Todo("todo_3",  LocalDate.of(2025, 1, 1), false, user)
        );

        todoRepository.saveAll(todos);
    }

    @Test
    void shouldFindAllByUserId() {
        List<Todo> result = todoRepository.findAllByUserId(user.getId());
        assertEquals(3, result.size());
    }

    @Test
    void shouldFindByIdAndUserId() {
        Todo todo = todoRepository.findAll().get(0);

        Optional<Todo> result = todoRepository.findByIdAndUserId(todo.getId(), user.getId());

        Todo todoResult = result.orElseThrow();

        assertEquals(todo.getDescription(), todoResult.getDescription());
        assertEquals(todo.getId(), todoResult.getId());
        assertEquals(user.getId(), todoResult.getUser().getId());
    }

    @Test
    void shouldNotFindByUserId() {
        User anotherUser = new User("anotherUsername", "password");
        userRepository.save(anotherUser);

        List<Todo> result = todoRepository.findAllByUserId(anotherUser.getId());

        assertTrue(result.isEmpty());
    }
}
