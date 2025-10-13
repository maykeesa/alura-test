package br.com.alura.AluraFake.user.dto;

import br.com.alura.AluraFake.course.dto.CourseDTO;
import br.com.alura.AluraFake.user.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.util.List;

import static br.com.alura.AluraFake.util.constants.MessageValidation.MSG_EMAIL_INVALID;
import static br.com.alura.AluraFake.util.constants.MessageValidation.MSG_NOT_NULL;
import static br.com.alura.AluraFake.util.constants.MessageValidation.MSG_NOT_NULL_OR_EMPTY;

public class UserDTO {

    public static class Request{

        @Data
        public static class Register{
            @NotNull(message = MSG_NOT_NULL)
            @Length(min = 3, max = 50, message = "The field must contain between 3 and 50 characters")
            private String name;

            @Email(message = MSG_EMAIL_INVALID)
            @NotBlank(message = MSG_NOT_NULL_OR_EMPTY)
            private String email;

            @Pattern(regexp = "STUDENT|INSTRUCTOR", message = "Role must be STUDENT or INSTRUCTOR")
            @NotNull(message = MSG_NOT_NULL)
            private String role;

            @NotBlank(message = MSG_NOT_NULL_OR_EMPTY)
            @Pattern(regexp = "^$|^.{6}$", message = "Password must be exactly 6 characters long if provided")
            private String password;
        }

    }

    public static class Response{

        @Data
        public static class User{
            private Long id;
            private String name;
            private String email;
            private Role role;
        }

        @Data
        @EqualsAndHashCode(callSuper = true)
        public static class Instructor extends User{
            private Integer numberCoursesPublished;
            private List<CourseDTO.Response.Course> courses;
        }
    }
}
