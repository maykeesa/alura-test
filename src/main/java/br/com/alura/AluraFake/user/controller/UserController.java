package br.com.alura.AluraFake.user.controller;

import br.com.alura.AluraFake.user.dto.UserDTO;
import br.com.alura.AluraFake.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO.Response.User>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/new")
    public ResponseEntity<UserDTO.Response.User> create(@RequestBody @Valid UserDTO.Request.Register body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(body));
    }
}
