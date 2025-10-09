package br.com.alura.AluraFake.course;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

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
        public static class Course implements Serializable {
            private Long id;
            private String title;
            private String description;
            private Status status;

//            public Course(br.com.alura.AluraFake.course.Course course){
//                this.id = course.getId();
//                this.title = course.getTitle();
//                this.description = course.getDescription();
//                this.status = course.getStatus();
//            }
        }
    }
}
