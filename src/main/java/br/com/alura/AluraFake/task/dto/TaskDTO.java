package br.com.alura.AluraFake.task.dto;

import br.com.alura.AluraFake.course.CourseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

import static br.com.alura.AluraFake.util.constants.MessageValidation.MSG_NOT_EMPTY;
import static br.com.alura.AluraFake.util.constants.MessageValidation.MSG_NOT_NULL;
import static br.com.alura.AluraFake.util.constants.MessageValidation.MSG_NOT_NULL_OR_EMPTY;
import static br.com.alura.AluraFake.util.constants.MessageValidation.MSG_POSITIVE;


public class TaskDTO {

    public static class Request{

        @Data
        private static class Base{
            @Positive(message = MSG_POSITIVE)
            @NotNull(message = MSG_NOT_NULL)
            private Long courseId;

            @NotBlank(message = MSG_NOT_NULL_OR_EMPTY)
            @Length(min = 4, max = 255, message = "The field must contain between 4 and 255 characters")
            private String statement;

            @Positive(message = MSG_POSITIVE)
            @NotNull(message = MSG_NOT_NULL)
            private Integer order;
        }

        @EqualsAndHashCode(callSuper = true)
        public static class OpenText extends Base{
        }

        @Data
        @EqualsAndHashCode(callSuper = true)
        public static class Choice extends Base{
            @NotEmpty(message = MSG_NOT_EMPTY)
            List<Options> options;
        }

        @Data
        private static class Options{
            private String option;
            private Boolean isCorrect;
        }
    }

    public static class Response{

        @Data
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class Task{
            private String statement;
            private String type;
            private Integer order;
        }

        @Data
        @RequiredArgsConstructor
        @AllArgsConstructor
        public static class OpenText{
            private CourseDTO.Response.Course course;
            List<TaskDTO.Response.Task> tasks;
        }

    }
}
