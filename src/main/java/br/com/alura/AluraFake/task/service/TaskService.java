package br.com.alura.AluraFake.task.service;

import br.com.alura.AluraFake.task.dto.TaskDTO;

public interface TaskService {

    TaskDTO.Response.OpenText newOpenTextExercise(TaskDTO.Request.OpenText body);
}
