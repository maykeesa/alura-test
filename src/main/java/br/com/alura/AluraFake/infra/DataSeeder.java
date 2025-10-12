package br.com.alura.AluraFake.infra;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.CourseRepository;
import br.com.alura.AluraFake.user.Role;
import br.com.alura.AluraFake.user.User;
import br.com.alura.AluraFake.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            User caio = new User("Caio", "caio@alura.com.br", Role.STUDENT);
            User paulo = new User("Paulo", "paulo@alura.com.br", Role.INSTRUCTOR);
            userRepository.saveAll(Arrays.asList(caio, paulo));
            courseRepository.save(new Course("Java", "Aprenda Java com Alura", paulo));
        }
    }
}