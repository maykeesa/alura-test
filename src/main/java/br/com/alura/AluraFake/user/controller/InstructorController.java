package br.com.alura.AluraFake.user.controller;

import br.com.alura.AluraFake.user.dto.UserDTO;
import br.com.alura.AluraFake.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/instructor")
@RequiredArgsConstructor
public class InstructorController {

    private final UserService userService;

    @GetMapping("/{id}/courses")
    public ResponseEntity<UserDTO.Response.Instructor> findInstructorById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findInstructorById(id));
    }
}
