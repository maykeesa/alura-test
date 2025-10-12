package br.com.alura.AluraFake.user.service;

import br.com.alura.AluraFake.user.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO.Response.User> findAll();

    UserDTO.Response.User create(UserDTO.Request.Register body);

    UserDTO.Response.Instructor findInstructorById(Long id);
}
