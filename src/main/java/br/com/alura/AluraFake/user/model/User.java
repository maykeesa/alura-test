package br.com.alura.AluraFake.user.model;

import br.com.alura.AluraFake.user.enums.Role;
import br.com.alura.AluraFake.util.PasswordGeneration;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String email;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt = LocalDateTime.now();

    public User(String name, String email, Role role) {
        this.name = name;
        this.role = role;
        this.email = email;
        this.password = PasswordGeneration.generatePassword();
    }

    public boolean isInstructor() {
        return Role.INSTRUCTOR.equals(this.role);
    }
}
