package br.com.alura.AluraFake.course;

import br.com.alura.AluraFake.config.security.jwt.JwtUtils;
import br.com.alura.AluraFake.course.dto.CourseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    @Sql(scripts = "/scripts/user/insert-user.sql")
    void newCourseDTO__should_return_created_when_new_course_request_is_valid() throws Exception {
        String jwtToken = "Bearer %s".formatted(
                jwtUtils.generateToken("john.doe@example.com", "teste123"));

        CourseDTO.Request.Register newCourseDTO = new CourseDTO.Request.Register();
        newCourseDTO.setTitle("Java");
        newCourseDTO.setDescription("Curso de Java");

        mockMvc.perform(post("/course/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", jwtToken)
                        .content(objectMapper.writeValueAsString(newCourseDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    @Sql(scripts = "/scripts/course/insert-course.sql")
    void listAllCourses__should_list_all_courses() throws Exception {
        String jwtToken = "Bearer %s".formatted(
                jwtUtils.generateToken("john.doe@example.com", "teste123"));

        mockMvc.perform(get("/course/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Introduction to Java"))
                .andExpect(jsonPath("$[0].description").value("Learn the basics of Java " +
                        "programming, syntax, and object-oriented concepts."))
                .andExpect(jsonPath("$[1].title").value("Advanced Java"))
                .andExpect(jsonPath("$[1].description").value("Deep dive into advanced Java " +
                        "topics, including streams, concurrency, and design patterns."));
    }

}