package br.com.alura.AluraFake.user.controller;

import br.com.alura.AluraFake.config.security.jwt.JwtUtils;
import br.com.alura.AluraFake.user.dto.LoginDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final JwtUtils jwtUtils;

    @PostMapping
    public ResponseEntity<LoginDTO.Response.Token> login(@RequestBody @Valid LoginDTO.Request.Login body) {
        String token = jwtUtils.generateToken(body.getEmail(), body.getPassword());
        return ResponseEntity.ok(LoginDTO.Response.Token.builder().type("Bearer").token(token).build());
    }
}