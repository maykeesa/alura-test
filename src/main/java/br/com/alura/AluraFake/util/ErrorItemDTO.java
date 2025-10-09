package br.com.alura.AluraFake.util;

import lombok.Data;
import org.springframework.util.Assert;

@Data
public class ErrorItemDTO {

    private final String field;
    private final String message;

    public ErrorItemDTO(String field, String message) {
        Assert.notNull(field, "field description must not be null");
        Assert.isTrue(!field.isEmpty(), "field description must not be null");
        Assert.notNull(message, "message description must not be null");
        Assert.isTrue(!message.isEmpty(), "message description must not be null");
        this.field = field;
        this.message = message;
    }
}
