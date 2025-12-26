package io.github.gabrielvavelar.todo.todo.controller;

import io.github.gabrielvavelar.todo.security.UserPrincipal;
import io.github.gabrielvavelar.todo.todo.dto.TodoRequestDto;
import io.github.gabrielvavelar.todo.todo.dto.TodoResponseDto;
import io.github.gabrielvavelar.todo.todo.service.TodoService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(TodoController.class)
public class TodoControllerTest {
    @Autowired
    private MockMvc mockMvc
            ;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TodoService todoService;

    @Test
    void shouldCreateTodo() throws Exception {
        TodoRequestDto requestDto = new TodoRequestDto(UUID.randomUUID(), "todo", LocalDate.of(2025, 1, 1));
        TodoResponseDto responseDto = new TodoResponseDto(UUID.randomUUID(), "todo", LocalDate.of(2025, 1, 1), false);
        UserPrincipal mockUser = Mockito.mock(UserPrincipal.class);

        Mockito.when(todoService.save(Mockito.any(TodoRequestDto.class), Mockito.any()))
                .thenReturn(responseDto);

        mockMvc.perform(
                        post("/todo")
                                .principal(new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                                        mockUser, null, java.util.Collections.emptyList()
                                ))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestDto))
                )

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.description").value("todo"))
                .andExpect(jsonPath("$.done").value(false));

        Mockito.verify(todoService).save(Mockito.any(TodoRequestDto.class), Mockito.any());
    }

    @Test
    void shouldDeleteTodo() throws Exception {
        UUID todoId = UUID.randomUUID();
        UserPrincipal mockUser = Mockito.mock(UserPrincipal.class);

        Mockito.doNothing().when(todoService).delete(Mockito.eq(todoId), Mockito.any());

        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/todo/{id}", todoId)
                                .principal(new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                                        mockUser, null, java.util.Collections.emptyList()
                                ))
                )
                .andExpect(status().isNoContent());

        Mockito.verify(todoService).delete(Mockito.eq(todoId), Mockito.any());
    }

    @Test
    void shouldUpdateTodo() throws Exception {
        UUID todoId = UUID.randomUUID();
        UserPrincipal mockUser = Mockito.mock(UserPrincipal.class);

        TodoRequestDto requestDto = new TodoRequestDto(UUID.randomUUID(), "todo", LocalDate.of(2025, 1, 1));

        TodoResponseDto responseDto = new TodoResponseDto(todoId, "todo", LocalDate.of(2025, 1, 1), false);

        Mockito.when(todoService.update(Mockito.eq(todoId), Mockito.any(TodoRequestDto.class), Mockito.any()))
                .thenReturn(responseDto);

        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/todo/{id}", todoId)
                                .principal(new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                                        mockUser, null, java.util.Collections.emptyList()
                                ))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(todoId.toString()))
                .andExpect(jsonPath("$.description").value("todo"));

        Mockito.verify(todoService).update(Mockito.eq(todoId), Mockito.any(TodoRequestDto.class), Mockito.any());
    }

    @Test
    void shouldGetAllTodos() throws Exception {
        UserPrincipal mockUser = Mockito.mock(UserPrincipal.class);

        TodoResponseDto todo = new TodoResponseDto(
                UUID.randomUUID(), "todo 1", LocalDate.of(2025, 1, 1), false
        );

        List<TodoResponseDto> todos = List.of(
                new TodoResponseDto(UUID.randomUUID(),"todo_1",  LocalDate.of(2025, 1, 1), false),
                new TodoResponseDto(UUID.randomUUID(),"todo_2",  LocalDate.of(2025, 1, 1), false)
        );

        Mockito.when(todoService.getAll(Mockito.any()))
                .thenReturn(todos);

        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/todo")
                                .principal(new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                                        mockUser, null, java.util.Collections.emptyList()
                                ))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].description").value("todo_1"))
                .andExpect(jsonPath("$[0].done").value(false));

        Mockito.verify(todoService).getAll(Mockito.any());
    }

    @Test
    void shouldToggleTodoDone() throws Exception {
        UUID todoId = UUID.randomUUID();
        UserPrincipal mockUser = Mockito.mock(UserPrincipal.class);

        TodoResponseDto responseDto = new TodoResponseDto(todoId, "todo toggle", LocalDate.of(2025, 1, 1), true);

        Mockito.when(todoService.toggleDone(Mockito.eq(todoId), Mockito.any()))
                .thenReturn(responseDto);

        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/todo/{id}/done", todoId)
                                .principal(new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                                        mockUser, null, java.util.Collections.emptyList()
                                ))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(todoId.toString()))
                .andExpect(jsonPath("$.done").value(true));

        Mockito.verify(todoService).toggleDone(Mockito.eq(todoId), Mockito.any());
    }
}
