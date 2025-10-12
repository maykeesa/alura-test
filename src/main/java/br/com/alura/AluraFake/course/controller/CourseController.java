package br.com.alura.AluraFake.course.controller;

import br.com.alura.AluraFake.course.dto.CourseDTO;
import br.com.alura.AluraFake.course.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/all")
    public ResponseEntity<List<CourseDTO.Response.Course>> findAll() {
        return ResponseEntity.ok(courseService.findAll());
    }

    @Transactional
    @PostMapping("/new")
    public ResponseEntity<CourseDTO.Response.Course> create(@RequestBody @Valid CourseDTO.Request.Register body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.create(body));
    }

    @PostMapping("/{id}/publish")
    public ResponseEntity<CourseDTO.Response.Course> publish(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(courseService.publish(id));
    }

}
