package br.com.alura.AluraFake.user;

import br.com.alura.AluraFake.config.security.jwt.JwtUtils;
import br.com.alura.AluraFake.user.dto.UserDTO;
import br.com.alura.AluraFake.user.enums.Role;
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
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    void newUser__should_return_bad_request_when_email_is_invalid() throws Exception {
        UserDTO.Request.Register newUserDTO = new UserDTO.Request.Register();
        newUserDTO.setEmail("caio");
        newUserDTO.setName("Caio Bugorin");
        newUserDTO.setRole(Role.STUDENT.toString());
        newUserDTO.setPassword("teste1");

        mockMvc.perform(post("/user/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUserDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.cause.email")
                        .value("The value passed must be an email address: example@mail.com"));
    }

    @Test
    @Sql(scripts = "/scripts/user/insert-user.sql")
    void newUser__should_return_bad_request_when_email_already_exists() throws Exception {
        UserDTO.Request.Register newUserDTO = new UserDTO.Request.Register();
        newUserDTO.setEmail("john.doe@example.com");
        newUserDTO.setName("João");
        newUserDTO.setRole(Role.STUDENT.toString());
        newUserDTO.setPassword("teste1");

        mockMvc.perform(post("/user/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUserDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.cause").value("Email already registered in the system."));
    }

    @Test
    void newUser__should_return_created_when_user_request_is_valid() throws Exception {
        UserDTO.Request.Register newUserDTO = new UserDTO.Request.Register();
        newUserDTO.setEmail("caio.bugorin@alura.com.br");
        newUserDTO.setName("Caio Bugorin");
        newUserDTO.setRole(Role.STUDENT.toString());
        newUserDTO.setPassword("teste1");

        mockMvc.perform(post("/user/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUserDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    @Sql(scripts = "/scripts/user/insert-user.sql")
    void listAllUsers__should_list_all_users() throws Exception {
        String jwtToken = "Bearer %s".formatted(
                jwtUtils.generateToken("john.doe@example.com", "teste123"));

        mockMvc.perform(get("/user/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Jane Smith"));
    }

}