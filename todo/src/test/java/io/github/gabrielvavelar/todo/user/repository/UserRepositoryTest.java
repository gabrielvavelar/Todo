package io.github.gabrielvavelar.todo.user.repository;

import io.github.gabrielvavelar.todo.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("username", "password");
        userRepository.save(user);
    }

    @Test
    void shouldFindByUsername() {
        Optional<User> result = userRepository.findByUsername(user.getUsername());
        assertTrue(result.isPresent());
        assertEquals(user.getId(), result.get().getId());
    }

    @Test
    void shouldExistByUsername() {
        assertTrue(userRepository.existsByUsername(user.getUsername()));
    }

    @Test
    void shouldNotFindByUsername() {
        Optional<User> result = userRepository.findByUsername("anotherUsername");
        assertFalse(result.isPresent());
    }

    @Test
    void shouldNotExistByUsername() {
        assertFalse(userRepository.existsByUsername("anotherUsername"));
    }
}
