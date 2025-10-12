package br.com.alura.AluraFake.course.service;

import br.com.alura.AluraFake.course.dto.CourseDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CourseService {

    List<CourseDTO.Response.Course> getAll();

    CourseDTO.Response.Course create(CourseDTO.Request.Register newCourse);

    CourseDTO.Response.Course publish(@PathVariable("id") Long id);
}
