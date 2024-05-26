package com.petshopgen.repository;

import com.petshopgen.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    boolean existsByAnimalIdAndDataAtendimento(Long animalId, LocalDateTime dataAtendimento);
}
