package br.com.alura.AluraFake.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import static br.com.alura.AluraFake.util.constants.MessageValidation.MSG_EMAIL_INVALID;
import static br.com.alura.AluraFake.util.constants.MessageValidation.MSG_NOT_NULL_OR_EMPTY;

public class LoginDTO {

    public static class Request {

        @Data
        public static class Login{
            @Email(message = MSG_EMAIL_INVALID)
            @NotBlank(message = MSG_NOT_NULL_OR_EMPTY)
            private String email;
            @NotBlank(message = MSG_NOT_NULL_OR_EMPTY)
            private String password;
        }
    }

    public static class Response {

        @Data
        @SuperBuilder
        public static class Token{
            private String token;
            private String type;
        }
    }

}
