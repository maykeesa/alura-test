package br.com.alura.AluraFake.task.controller.arguments;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static br.com.alura.AluraFake.task.service.helper.TaskServiceHelperTest.requestMultipleChoice;
import static br.com.alura.AluraFake.task.service.helper.TaskServiceHelperTest.requestOpenText;
import static br.com.alura.AluraFake.task.service.helper.TaskServiceHelperTest.requestSingleChoice;

@Component
public class TaskControllerTestArguments {

    public static Stream<Arguments> provideInvalidOpenTextTasks() throws Exception {
        return Stream.of(
                Arguments.of(updateValueJson(bodyOpenText(), Map.of("courseId", 3)),
                        "Course with id 3 not found.", 404),
                Arguments.of(updateValueJson(bodyOpenText(), Map.of("courseId", 2)),
                        "Cannot add a new task, course is not in BUILDING status.", 400),
                Arguments.of(updateValueJson(bodyOpenText(), Map.of("courseId", "null")),
                        "It cannot be null", 400),
                Arguments.of(updateValueJson(bodyOpenText(), Map.of("courseId", -1)),
                        "The value must be positive.", 400),
                Arguments.of(updateValueJson(bodyOpenText(), Map.of("statement", " ")),
                        "It cannot be null or empty", 400),
                Arguments.of(updateValueJson(bodyOpenText(), Map.of("statement", "A")),
                        "The field must contain between 4 and 255 characters", 400),
                Arguments.of(updateValueJson(bodyOpenText(), Map.of("statement", "A".repeat(256))),
                        "The field must contain between 4 and 255 characters", 400),
                Arguments.of(updateValueJson(bodyOpenText(), Map.of("statement",
                                "What is object-oriented programming (OOP)? Describe its main principles.")),
                        "There is already a task with the same statement.", 400),
                Arguments.of(updateValueJson(bodyOpenText(), Map.of("order", 3)),
                        "Order exceeds the maximum allowed position (2)", 400),
                Arguments.of(updateValueJson(bodyOpenText(), Map.of("order", -1)),
                        "The value must be positive.", 400),
                Arguments.of(updateValueJson(bodyOpenText(), Map.of("order", "null")),
                        "It cannot be null", 400)
        );
    }

    public static Stream<Arguments> provideInvalidSingleChoiceTasks() throws Exception {
        return Stream.of(
                Arguments.of(updateValueJson(bodySingleChoice(), Map.of("courseId", 3)),
                        "Course with id 3 not found.", 404),
                Arguments.of(updateValueJson(bodySingleChoice(), Map.of("courseId", 2)),
                        "Cannot add a new task, course is not in BUILDING status.", 400),
                Arguments.of(updateValueJson(bodySingleChoice(), Map.of("courseId", "null")),
                        "It cannot be null", 400),
                Arguments.of(updateValueJson(bodySingleChoice(), Map.of("courseId", -1)),
                        "The value must be positive.", 400),
                Arguments.of(updateValueJson(bodySingleChoice(), Map.of("statement", " ")),
                        "It cannot be null or empty", 400),
                Arguments.of(updateValueJson(bodySingleChoice(), Map.of("statement", "A")),
                        "The field must contain between 4 and 255 characters", 400),
                Arguments.of(updateValueJson(bodySingleChoice(), Map.of("statement", "A".repeat(256))),
                        "The field must contain between 4 and 255 characters", 400),
                Arguments.of(updateValueJson(bodySingleChoice(), Map.of("statement",
                                "What is object-oriented programming (OOP)? Describe its main principles.")),
                        "There is already a task with the same statement.", 400),
                Arguments.of(updateValueJson(bodySingleChoice(), Map.of("order", 3)),
                        "Order exceeds the maximum allowed position (2)", 400),
                Arguments.of(updateValueJson(bodySingleChoice(), Map.of("order", -1)),
                        "The value must be positive.", 400),
                Arguments.of(updateValueJson(bodySingleChoice(), Map.of("order", "null")),
                        "It cannot be null", 400),
                Arguments.of(updateValueJson(bodySingleChoice(), Map.of("options",
                                List.of(Map.of("option", "Polymorphism", "isCorrect", true),
                                        Map.of("option", "Abstraction", "isCorrect", false),
                                        Map.of("option", "Encapsulation", "isCorrect", true)
                                )
                        )),
                        "There should only be one correct option.", 400),
                Arguments.of(updateValueJson(bodySingleChoice(), Map.of("options",
                                List.of(Map.of("option", "Polymorphism", "isCorrect", false),
                                        Map.of("option", "Abstraction", "isCorrect", false),
                                        Map.of("option", "Encapsulation", "isCorrect", false)
                                )
                        )),
                        "The task must have at least one correct alternative.", 400),
                Arguments.of(updateValueJson(bodySingleChoice(), Map.of("options",
                                List.of(Map.of("option", "Polymorphism", "isCorrect", true),
                                        Map.of("option", "Polymorphism", "isCorrect", false),
                                        Map.of("option", "Encapsulation", "isCorrect", false)
                                )
                        )),
                        "There is at least one duplicate option for this: Polymorphism", 400),
                Arguments.of(updateValueJson(bodySingleChoice(), Map.of("options",
                                List.of(Map.of("option", "What is the best language?", "isCorrect", true),
                                        Map.of("option", "Polymorphism", "isCorrect", false),
                                        Map.of("option", "Encapsulation", "isCorrect", false)
                                )
                        )),
                        "The alternatives cannot be the same as the statement.", 400),
                Arguments.of(updateValueJson(bodySingleChoice(), Map.of("options",
                                List.of(Map.of("option", "What is the best language?", "isCorrect", true)
                                )
                        )),
                        "Single-choice questions should have between two and five alternatives.", 400),
                Arguments.of(updateValueJson(bodySingleChoice(), Map.of("options",
                                List.of(Map.of("option", "Polymorphism", "isCorrect", false),
                                        Map.of("option", "Abstraction", "isCorrect", false),
                                        Map.of("option", "Encapsulation", "isCorrect", false),
                                        Map.of("option", "Inheritance", "isCorrect", true),
                                        Map.of("option", "Interface", "isCorrect", false),
                                        Map.of("option", "Composition", "isCorrect", true)
                                )
                        )),
                        "Single-choice questions should have between two and five alternatives.", 400),
                Arguments.of(updateValueJson(bodySingleChoice(), Map.of("options",
                                List.of(Map.of("option", "A", "isCorrect", true),
                                        Map.of("option", "Abstraction", "isCorrect", false),
                                        Map.of("option", "Encapsulation", "isCorrect", false)
                                )
                        )),
                        "The field must contain between 4 and 80 characters", 400),
                Arguments.of(updateValueJson(bodySingleChoice(), Map.of("options",
                                List.of(Map.of("option", "A".repeat(81), "isCorrect", true),
                                        Map.of("option", "Abstraction", "isCorrect", false),
                                        Map.of("option", "Encapsulation", "isCorrect", false)
                                )
                        )),
                        "The field must contain between 4 and 80 characters", 400),
                Arguments.of(updateValueJson(bodySingleChoice(), Map.of("options", List.of())),
                        "It cannot be empty.", 400),
                Arguments.of(updateValueJson(bodySingleChoice(), Map.of("options",
                                List.of(Map.of("option", "Polymorphism", "isCorrect", true),
                                        Map.of("option", "Abstraction", "isCorrect", "null"),
                                        Map.of("option", "Encapsulation", "isCorrect", false)
                                )
                        )),
                        "It cannot be null.", 400)
        );
    }

    public static Stream<Arguments> provideInvalidMultipleChoiceTasks() throws Exception {
        return Stream.of(
                Arguments.of(updateValueJson(bodyMultipleChoice(), Map.of("courseId", 3)),
                        "Course with id 3 not found.", 404),
                Arguments.of(updateValueJson(bodyMultipleChoice(), Map.of("courseId", 2)),
                        "Cannot add a new task, course is not in BUILDING status.", 400),
                Arguments.of(updateValueJson(bodyMultipleChoice(), Map.of("courseId", "null")),
                        "It cannot be null", 400),
                Arguments.of(updateValueJson(bodyMultipleChoice(), Map.of("courseId", -1)),
                        "The value must be positive.", 400),
                Arguments.of(updateValueJson(bodyMultipleChoice(), Map.of("statement", " ")),
                        "It cannot be null or empty", 400),
                Arguments.of(updateValueJson(bodyMultipleChoice(), Map.of("statement", "A")),
                        "The field must contain between 4 and 255 characters", 400),
                Arguments.of(updateValueJson(bodyMultipleChoice(), Map.of("statement", "A".repeat(256))),
                        "The field must contain between 4 and 255 characters", 400),
                Arguments.of(updateValueJson(bodyMultipleChoice(), Map.of("statement",
                                "What is object-oriented programming (OOP)? Describe its main principles.")),
                        "There is already a task with the same statement.", 400),
                Arguments.of(updateValueJson(bodyMultipleChoice(), Map.of("order", 3)),
                        "Order exceeds the maximum allowed position (2)", 400),
                Arguments.of(updateValueJson(bodyMultipleChoice(), Map.of("order", -1)),
                        "The value must be positive.", 400),
                Arguments.of(updateValueJson(bodyMultipleChoice(), Map.of("order", "null")),
                        "It cannot be null", 400),
                Arguments.of(updateValueJson(bodyMultipleChoice(), Map.of("options",
                                List.of(Map.of("option", "Polymorphism", "isCorrect", true),
                                        Map.of("option", "Abstraction", "isCorrect", false),
                                        Map.of("option", "Encapsulation", "isCorrect", false)
                                )
                        )),
                        "There must be at least two correct options.", 400),
                Arguments.of(updateValueJson(bodyMultipleChoice(), Map.of("options",
                                List.of(Map.of("option", "Polymorphism", "isCorrect", false),
                                        Map.of("option", "Abstraction", "isCorrect", false),
                                        Map.of("option", "Encapsulation", "isCorrect", false)
                                )
                        )),
                        "The task must have at least one correct alternative.", 400),
                Arguments.of(updateValueJson(bodyMultipleChoice(), Map.of("options",
                                List.of(Map.of("option", "Polymorphism", "isCorrect", true),
                                        Map.of("option", "Polymorphism", "isCorrect", false),
                                        Map.of("option", "Encapsulation", "isCorrect", true)
                                )
                        )),
                        "There is at least one duplicate option for this: Polymorphism", 400),
                Arguments.of(updateValueJson(bodyMultipleChoice(), Map.of("options",
                                List.of(Map.of("option", "What is the best language?", "isCorrect", true),
                                        Map.of("option", "Polymorphism", "isCorrect", false),
                                        Map.of("option", "Encapsulation", "isCorrect", true)
                                )
                        )),
                        "The alternatives cannot be the same as the statement.", 400),
                Arguments.of(updateValueJson(bodyMultipleChoice(), Map.of("options",
                                List.of(Map.of("option", "What is the best language?", "isCorrect", true)
                                )
                        )),
                        "Multiple-choice questions should have between three and five alternatives.", 400),
                Arguments.of(updateValueJson(bodyMultipleChoice(), Map.of("options",
                                List.of(Map.of("option", "Polymorphism", "isCorrect", false),
                                        Map.of("option", "Abstraction", "isCorrect", false),
                                        Map.of("option", "Encapsulation", "isCorrect", false),
                                        Map.of("option", "Inheritance", "isCorrect", true),
                                        Map.of("option", "Interface", "isCorrect", false),
                                        Map.of("option", "Composition", "isCorrect", true)
                                )
                        )),
                        "Multiple-choice questions should have between three and five alternatives.", 400),
                Arguments.of(updateValueJson(bodyMultipleChoice(), Map.of("options",
                                List.of(Map.of("option", "A", "isCorrect", true),
                                        Map.of("option", "Abstraction", "isCorrect", false),
                                        Map.of("option", "Encapsulation", "isCorrect", false)
                                )
                        )),
                        "The field must contain between 4 and 80 characters", 400),
                Arguments.of(updateValueJson(bodyMultipleChoice(), Map.of("options",
                                List.of(Map.of("option", "A".repeat(81), "isCorrect", true),
                                        Map.of("option", "Abstraction", "isCorrect", false),
                                        Map.of("option", "Encapsulation", "isCorrect", false)
                                )
                        )),
                        "The field must contain between 4 and 80 characters", 400),
                Arguments.of(updateValueJson(bodyMultipleChoice(), Map.of("options", List.of())),
                        "It cannot be empty.", 400),
                Arguments.of(updateValueJson(bodyMultipleChoice(), Map.of("options",
                                List.of(Map.of("option", "Polymorphism", "isCorrect", true),
                                        Map.of("option", "Abstraction", "isCorrect", "null"),
                                        Map.of("option", "Encapsulation", "isCorrect", false)
                                )
                        )),
                        "It cannot be null.", 400)
        );
    }

    public static String bodyOpenText() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(requestOpenText());
    }

    public static String bodySingleChoice() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(requestSingleChoice());
    }

    public static String bodyMultipleChoice() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(requestMultipleChoice());
    }

    private static String updateValueJson(String jsonStr, Map<String, Object> changes) throws Exception {
        ObjectNode node = (ObjectNode) new ObjectMapper().readTree(jsonStr);

        changes.forEach((key, value) -> {
            switch (value) {
                case null -> node.putNull(key);
                case String s -> node.put(key, s);
                case Integer i -> node.put(key, i);
                case Boolean b -> node.put(key, b);
                default ->
                        node.set(key, new ObjectMapper().valueToTree(value));
            }
        });

        return node.toString();
    }
}
