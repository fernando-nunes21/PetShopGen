package com.petshopgen.controller;

import com.petshopgen.model.Animal;
import com.petshopgen.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/animals")
public class AnimalController {

    @Autowired
    private AnimalRepository animalRepository;

    @GetMapping
    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Animal> getAnimalById(@PathVariable Long id) {
        Optional<Animal> animal = animalRepository.findById(id);
        return animal.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Animal createAnimal(@RequestBody Animal animal) {
        return animalRepository.save(animal);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Animal> updateAnimal(@PathVariable Long id, @RequestBody Animal animalDetails) {
        Optional<Animal> animal = animalRepository.findById(id);
        if (animal.isPresent()) {
            Animal existingAnimal = animal.get();
            existingAnimal.setNome(animalDetails.getNome());
            existingAnimal.setIdade(animalDetails.getIdade());
            existingAnimal.setRaca(animalDetails.getRaca());
            Animal updatedAnimal = animalRepository.save(existingAnimal);
            return ResponseEntity.ok(updatedAnimal);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long id) {
        Optional<Animal> animal = animalRepository.findById(id);
        if (animal.isPresent()) {
            animalRepository.delete(animal.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
