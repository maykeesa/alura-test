package br.com.alura.AluraFake.task.controller;

import br.com.alura.AluraFake.config.security.jwt.JwtUtils;
import br.com.alura.AluraFake.task.controller.arguments.TaskControllerTestArguments;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private final String URL = "/task";

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    @Sql(scripts = "/scripts/course/insert-course.sql")
    @DisplayName("When registering an open text task, it should return success.")
    public void whenCreatingOpenTextTaskReturnSucess() throws Exception {
        String jwtToken = "Bearer %s".formatted(
                jwtUtils.generateToken("john.doe@example.com", "teste123"));

        mockMvc.perform(
                        post(URL + "/new/opentext")
                                .content(TaskControllerTestArguments.bodyOpenText())
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", jwtToken))
                .andExpect(status().isCreated() )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.course").exists())
                .andExpect(jsonPath("$.course.title").value("Introduction to Java"))
                .andExpect(jsonPath("$.tasks").exists())
                .andExpect(jsonPath("$.tasks", hasSize(2)))
                .andExpect(jsonPath("$.tasks.[0].statement").value("What is POO? And what are its pillars?"));
    }

    @ParameterizedTest
    @Sql(scripts = "/scripts/course/insert-course.sql")
    @DisplayName("When registering an open text task, it should return exception.")
    @MethodSource("br.com.alura.AluraFake.task.controller.arguments.TaskControllerTestArguments#provideInvalidOpenTextTasks")
    public void whenCreatingOpenTextTaskReturnException(String body, String error, Integer status) throws Exception {
        String jwtToken = "Bearer %s".formatted(
                jwtUtils.generateToken("john.doe@example.com", "teste123"));

        MvcResult mvcResult = mockMvc.perform(
                        post(URL + "/new/opentext")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", jwtToken))
                .andExpect(status().is(status)).andReturn();

        Assertions.assertThat(mvcResult.getResolvedException()).isNotNull();
        Assertions.assertThat(mvcResult.getResolvedException().getMessage()).contains(error);
    }

    @Test
    @Sql(scripts = "/scripts/course/insert-course.sql")
    @DisplayName("When registering an single choice task, it should return success.")
    void whenCreatingSingleChoiceTaskReturnSucess() throws Exception {
        String jwtToken = "Bearer %s".formatted(
                jwtUtils.generateToken("john.doe@example.com", "teste123"));

        mockMvc.perform(
                        post(URL + "/new/singlechoice")
                                .content(TaskControllerTestArguments.bodySingleChoice())
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", jwtToken))
                .andExpect(status().isCreated() )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.course").exists())
                .andExpect(jsonPath("$.course.title").value("Introduction to Java"))
                .andExpect(jsonPath("$.tasks").exists())
                .andExpect(jsonPath("$.tasks", hasSize(2)))
                .andExpect(jsonPath("$.tasks.[0].statement").value("What is the best language?"))
                .andExpect(jsonPath("$.tasks.[0].options.[0].option").value("Java"))
                .andExpect(jsonPath("$.tasks.[0].options.[1].isCorrect").value(true));
    }

    @ParameterizedTest
    @Sql(scripts = "/scripts/course/insert-course.sql")
    @DisplayName("When registering an single choice task, it should return exception.")
    @MethodSource("br.com.alura.AluraFake.task.controller.arguments.TaskControllerTestArguments#provideInvalidSingleChoiceTasks")
    public void whenCreatingSingleChoiceTaskReturnException(String body, String error, Integer status) throws Exception {
        String jwtToken = "Bearer %s".formatted(
                jwtUtils.generateToken("john.doe@example.com", "teste123"));

        MvcResult mvcResult = mockMvc.perform(
                        post(URL + "/new/singlechoice")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", jwtToken))
                .andExpect(status().is(status)).andReturn();

        Assertions.assertThat(mvcResult.getResolvedException()).isNotNull();
        Assertions.assertThat(mvcResult.getResolvedException().getMessage()).contains(error);
    }

    @Test
    @Sql(scripts = "/scripts/course/insert-course.sql")
    @DisplayName("When registering an multiple choice task, it should return success.")
    void whenCreatingMultipleChoiceTaskReturnSucess() throws Exception {
        String jwtToken = "Bearer %s".formatted(
                jwtUtils.generateToken("john.doe@example.com", "teste123"));

        mockMvc.perform(
                        post(URL + "/new/multiplechoice")
                                .content(TaskControllerTestArguments.bodyMultipleChoice())
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", jwtToken))
                .andExpect(status().isCreated() )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.course").exists())
                .andExpect(jsonPath("$.course.title").value("Introduction to Java"))
                .andExpect(jsonPath("$.tasks").exists())
                .andExpect(jsonPath("$.tasks", hasSize(2)))
                .andExpect(jsonPath("$.tasks.[0].statement").value("What is the best language?"))
                .andExpect(jsonPath("$.tasks.[0].options.[0].option").value("Java"))
                .andExpect(jsonPath("$.tasks.[0].options.[0].isCorrect").value(true))
                .andExpect(jsonPath("$.tasks.[0].options.[1].isCorrect").value(true));
    }

    @ParameterizedTest
    @Sql(scripts = "/scripts/course/insert-course.sql")
    @DisplayName("When registering an multiple choice task, it should return exception.")
    @MethodSource("br.com.alura.AluraFake.task.controller.arguments.TaskControllerTestArguments#provideInvalidMultipleChoiceTasks")
    public void whenCreatingMultipleChoiceTaskReturnException(String body, String error, Integer status) throws Exception {
        String jwtToken = "Bearer %s".formatted(
                jwtUtils.generateToken("john.doe@example.com", "teste123"));

        MvcResult mvcResult = mockMvc.perform(
                        post(URL + "/new/multiplechoice")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", jwtToken))
                .andExpect(status().is(status)).andReturn();

        Assertions.assertThat(mvcResult.getResolvedException()).isNotNull();
        Assertions.assertThat(mvcResult.getResolvedException().getMessage()).contains(error);
    }
}