package io.github.gabrielvavelar.todo.todo.model;

import io.github.gabrielvavelar.todo.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tb_todo")
@Getter
@Setter
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String description;
    private LocalDate date;
    private boolean done = false;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Todo() {}

    public Todo(String description, LocalDate date, User user) {
        this.description = description;
        this.date = date;
        this.user = user;
    }
}
