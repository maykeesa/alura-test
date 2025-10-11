package br.com.alura.AluraFake.task.controller;

import br.com.alura.AluraFake.task.dto.TaskDTO;
import br.com.alura.AluraFake.task.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/new/opentext")
    public ResponseEntity<TaskDTO.Response.Tasks> newOpenTextExercise(@RequestBody @Valid TaskDTO.Request.OpenText body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.newOpenTextExercise(body));
    }

    @PostMapping("/new/singlechoice")
    public ResponseEntity<TaskDTO.Response.Tasks> newSingleChoice(@RequestBody @Valid TaskDTO.Request.Choice body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.newSingleChoice(body));
    }

    @PostMapping("/new/multiplechoice")
    public ResponseEntity newMultipleChoice(@RequestBody @Valid TaskDTO.Request.Choice body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.newMultipleChoice(body));
    }

}