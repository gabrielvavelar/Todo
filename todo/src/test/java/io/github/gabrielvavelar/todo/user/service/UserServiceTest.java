package io.github.gabrielvavelar.todo.user.service;

import io.github.gabrielvavelar.todo.user.dto.UserRequestDto;
import io.github.gabrielvavelar.todo.user.dto.UserResponseDto;
import io.github.gabrielvavelar.todo.user.exception.UsernameAlreadyExistsException;
import io.github.gabrielvavelar.todo.user.mapper.UserMapper;
import io.github.gabrielvavelar.todo.user.model.User;
import io.github.gabrielvavelar.todo.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserMapper userMapper;

    @Test
    void shouldSaveUser() {
        UserRequestDto dto = new UserRequestDto("username", "password");

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("username");
        user.setPassword("password");

        UserResponseDto responseDto = new UserResponseDto(user.getId(), "username");

        Mockito.when(userRepository.existsByUsername("username"))
                .thenReturn(false);
        Mockito.when(userMapper.toEntity(dto))
                .thenReturn(user);
        Mockito.when(passwordEncoder.encode("password"))
                .thenReturn("encoded");
        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenReturn(user);
        Mockito.when(userMapper.toResponse(user))
                .thenReturn(responseDto);

        UserResponseDto result = userService.save(dto);

        assertThat(result).isNotNull();
        assertThat(result.username()).isEqualTo("username");

        Mockito.verify(userRepository).save(
                Mockito.argThat(savedUser ->
                        savedUser.getPassword().equals("encoded")
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenUsernameAlreadyExists() {
        UserRequestDto dto = new UserRequestDto("username", "password");
        Mockito.when(userRepository.existsByUsername("username"))
                .thenReturn(true);

        var error = catchThrowable(() -> userService.save(dto));
        assertThat(error).isInstanceOf(UsernameAlreadyExistsException.class);

        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any());
    }
}
