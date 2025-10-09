package br.com.alura.AluraFake.course;

import br.com.alura.AluraFake.task.model.Task;
import br.com.alura.AluraFake.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    private User instructor;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    private LocalDateTime publishedAt;
    @CreationTimestamp
    private LocalDateTime createdAt;

    public Course(String title, String description, User instructor) {
        Assert.isTrue(instructor.isInstructor(), "Usuario deve ser um instrutor");
        this.title = title;
        this.instructor = instructor;
        this.description = description;
        this.status = Status.BUILDING;
    }
}
