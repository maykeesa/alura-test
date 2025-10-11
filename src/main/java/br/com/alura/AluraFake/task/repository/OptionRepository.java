package br.com.alura.AluraFake.task.repository;

import br.com.alura.AluraFake.task.model.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository<Option, Integer> {
}
