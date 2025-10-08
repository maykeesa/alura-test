package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.CourseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import static br.com.alura.AluraFake.util.Constants.MessageValidation.MSG_NOT_NULL;
import static br.com.alura.AluraFake.util.Constants.MessageValidation.MSG_NOT_NULL_OR_EMPTY;
import static br.com.alura.AluraFake.util.Constants.MessageValidation.MSG_POSITIVE;


public class TaskDTO {

    public static class Request{

        @Data
        public static class OpenText{
            @Positive(message = MSG_POSITIVE)
            @NotNull(message = MSG_NOT_NULL)
            private Integer courseId;

            @NotBlank(message = MSG_NOT_NULL_OR_EMPTY)
            @Length(min = 4, max = 255, message = "The field must contain between 4 and 255 characters")
            private String statement;

            @Positive(message = MSG_POSITIVE)
            @NotNull(message = MSG_NOT_NULL)
            private Integer order;
        }
    }

    public static class Response{

        @Data
        public static class OpenText{
            private CourseDTO.Response.Course course;
            private String statement;
            private Integer order;
        }

    }
}
