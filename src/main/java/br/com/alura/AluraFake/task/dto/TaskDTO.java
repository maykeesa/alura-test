package br.com.alura.AluraFake.task.dto;

import br.com.alura.AluraFake.course.CourseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static br.com.alura.AluraFake.util.constants.MessageValidation.MSG_MIN_MAX_OPTIONS_TASK_MULTIPLE_CHOICE;
import static br.com.alura.AluraFake.util.constants.MessageValidation.MSG_MIN_MAX_OPTIONS_TASK_SINGLE_CHOICE;
import static br.com.alura.AluraFake.util.constants.MessageValidation.MSG_NOT_EMPTY;
import static br.com.alura.AluraFake.util.constants.MessageValidation.MSG_NOT_NULL;
import static br.com.alura.AluraFake.util.constants.MessageValidation.MSG_NOT_NULL_OR_EMPTY;
import static br.com.alura.AluraFake.util.constants.MessageValidation.MSG_POSITIVE;

public class TaskDTO {

    public static class Request{

        public interface Task{
            Long getCourseId();
        }

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

        @Data
        @EqualsAndHashCode(callSuper = true)
        public static class OpenText extends Base implements Task{
        }

        @Data
        @Validated
        @EqualsAndHashCode(callSuper = true)
        public static class SingleChoice extends Base implements Task{
            @Valid
            @NotEmpty(message = MSG_NOT_EMPTY)
            @Size(min = 2, max = 5, message = MSG_MIN_MAX_OPTIONS_TASK_SINGLE_CHOICE)
            List<Options> options;
        }

        @Data
        @Validated
        @EqualsAndHashCode(callSuper = true)
        public static class MultipleChoice extends Base implements Task{
            @Valid
            @NotEmpty(message = MSG_NOT_EMPTY)
            @Size(min = 3, max = 5, message = MSG_MIN_MAX_OPTIONS_TASK_MULTIPLE_CHOICE)
            List<Options> options;
        }

        @Data
        private static class Options{
            @Length(min = 4, max = 80, message = "The field must contain between 4 and 80 characters")
            private String option;
            @NotNull(message = MSG_NOT_NULL)
            private Boolean isCorrect;
        }
    }

    public static class Response{

        @Data
        public static class Task{
            private Long id;
            private String statement;
            private String type;
            private Integer order;
            @JsonInclude(JsonInclude.Include.NON_EMPTY)
            private List<TaskDTO.Response.Options> options;
        }

        @Data
        @AllArgsConstructor
        @RequiredArgsConstructor
        public static class Tasks {
            private CourseDTO.Response.Course course;
            List<TaskDTO.Response.Task> tasks;
        }

        @Data
        private static class Options{
            private Long id;
            private String option;
            private Boolean isCorrect;
        }

    }
}
