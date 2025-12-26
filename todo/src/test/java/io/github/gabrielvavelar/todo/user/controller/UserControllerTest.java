package io.github.gabrielvavelar.todo.user.controller;

import io.github.gabrielvavelar.todo.user.dto.UserRequestDto;
import io.github.gabrielvavelar.todo.user.dto.UserResponseDto;
import io.github.gabrielvavelar.todo.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.http.MediaType;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc
            ;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @Test
    void shouldCreateUser() throws Exception {
        UserRequestDto requestDto = new UserRequestDto("username", "password");

        UserResponseDto responseDto = new UserResponseDto(java.util.UUID.randomUUID(), "username");

        Mockito.when(userService.save(Mockito.any(UserRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(
                        post("/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestDto))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("username"));

        Mockito.verify(userService).save(Mockito.any(UserRequestDto.class));
    }
}
