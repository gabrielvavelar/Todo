package io.github.gabrielvavelar.todo.user.model;

import io.github.gabrielvavelar.todo.todo.model.Todo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tb_user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username")
})
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(unique = true)
    private String username;
    private String password;

    public User() {}

    public User(String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
