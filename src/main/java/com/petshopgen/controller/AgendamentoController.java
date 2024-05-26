package com.petshopgen.controller;

import com.petshopgen.model.Agendamento;
import com.petshopgen.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @GetMapping
    public List<Agendamento> getAllAgendamentos() {
        return agendamentoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agendamento> getAgendamentoById(@PathVariable Long id) {
        Optional<Agendamento> agendamento = agendamentoRepository.findById(id);
        return agendamento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Agendamento> createAgendamento(@RequestBody Agendamento agendamento) {
        if (agendamentoRepository.existsByAnimalIdAndDataAtendimento(agendamento.getAnimalId(), agendamento.getDataAtendimento())) {
            return ResponseEntity.status(409).build();
        }
        Agendamento savedAgendamento = agendamentoRepository.save(agendamento);
        return ResponseEntity.ok(savedAgendamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agendamento> updateAgendamento(@PathVariable Long id, @RequestBody Agendamento agendamentoDetails) {
        Optional<Agendamento> agendamento = agendamentoRepository.findById(id);
        if (agendamento.isPresent()) {
            Agendamento existingAgendamento = agendamento.get();
            existingAgendamento.setAnimalId(agendamentoDetails.getAnimalId());
            existingAgendamento.setNome(agendamentoDetails.getNome());
            existingAgendamento.setDataAtendimento(agendamentoDetails.getDataAtendimento());
            Agendamento updatedAgendamento = agendamentoRepository.save(existingAgendamento);
            return ResponseEntity.ok(updatedAgendamento);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgendamento(@PathVariable Long id) {
        Optional<Agendamento> agendamento = agendamentoRepository.findById(id);
        if (agendamento.isPresent()) {
            agendamentoRepository.delete(agendamento.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
