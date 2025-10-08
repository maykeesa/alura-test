package br.com.alura.AluraFake.course;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

public class CourseDTO {

    public static class Request{

        @Data
        public static class Register{
            @NotBlank(message = )
            private String title;
            @NotBlank(message = )
            @Length(min = 4, max = 255)
            private String description;
            @NotBlank(message = )
            @Email(message = )
            private String emailInstructor;
        }

    }

    public static class Response{

        @Data
        public static class Course implements Serializable {
            private Long id;
            private String title;
            private String description;
            private Status status;

            public Course(br.com.alura.AluraFake.course.Course course){
                this.id = course.getId();
                this.title = course.getTitle();
                this.description = course.getDescription();
                this.status = course.getStatus();
            }
        }
    }
}
