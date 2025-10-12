package br.com.alura.AluraFake.course.dto;

import br.com.alura.AluraFake.course.enums.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDateTime;

import static br.com.alura.AluraFake.util.constants.MessageValidation.MSG_EMAIL_INVALID;
import static br.com.alura.AluraFake.util.constants.MessageValidation.MSG_NOT_NULL_OR_EMPTY;

public class CourseDTO {

    public static class Request{

        @Data
        public static class Register{
            @NotBlank(message = MSG_NOT_NULL_OR_EMPTY)
            private String title;

            @NotBlank(message = MSG_NOT_NULL_OR_EMPTY)
            @Length(min = 4, max = 255, message = "The field must contain between 4 and 255 characters")
            private String description;

            @NotBlank(message = MSG_NOT_NULL_OR_EMPTY)
            @Email(message = MSG_EMAIL_INVALID)
            private String emailInstructor;
        }

    }

    public static class Response{

        @Data
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class Course implements Serializable {
            private Long id;
            private String title;
            private Integer numberTasksCourse;
            private String description;
            private Status status;
            private LocalDateTime publishedAt;
            private LocalDateTime createdAt;
        }
    }
}
