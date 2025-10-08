package br.com.alura.AluraFake.config.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ErrorDTO {

    public static class Response{

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Error{
            private int status;
            private Object error;
            private Object cause;
        }
    }
}
