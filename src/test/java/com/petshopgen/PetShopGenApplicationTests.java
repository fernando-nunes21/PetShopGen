package com.petshopgen;

import com.petshopgen.controller.AnimalController;
import com.petshopgen.controller.AgendamentoController;
import com.petshopgen.controller.CatFactController;
import com.petshopgen.model.Animal;
import com.petshopgen.model.Agendamento;
import com.petshopgen.model.CatFact;
import com.petshopgen.repository.AnimalRepository;
import com.petshopgen.repository.AgendamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PetShopGenApplicationTests {

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AnimalController animalController;

    @InjectMocks
    private AgendamentoController agendamentoController;

    @InjectMocks
    private CatFactController catFactController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAnimals() {
        Animal animal = new Animal();
        animal.setNome("Rex");
        animal.setIdade(5);
        animal.setRaca("Labrador");
        when(animalRepository.findAll()).thenReturn(Arrays.asList(animal));
        List<Animal> animals = animalController.getAllAnimals();
        assertEquals(1, animals.size());
        assertEquals("Rex", animals.get(0).getNome());
    }

    @Test
    void testCreateAnimal() {
        Animal animal = new Animal();
        animal.setNome("Rex");
        animal.setIdade(5);
        animal.setRaca("Labrador");
        when(animalRepository.save(any(Animal.class))).thenReturn(animal);
        Animal createdAnimal = animalController.createAnimal(animal);
        assertEquals("Rex", createdAnimal.getNome());
    }

    @Test
    void testGetAnimalById() {
        Animal animal = new Animal();
        animal.setNome("Rex");
        animal.setIdade(5);
        animal.setRaca("Labrador");
        when(animalRepository.findById(anyLong())).thenReturn(Optional.of(animal));
        ResponseEntity<Animal> response = animalController.getAnimalById(1L);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("Rex", response.getBody().getNome());
    }

    @Test
    void testUpdateAnimal() {
        Animal animal = new Animal();
        animal.setNome("Rex");
        animal.setIdade(5);
        animal.setRaca("Labrador");
        when(animalRepository.findById(anyLong())).thenReturn(Optional.of(animal));
        when(animalRepository.save(any(Animal.class))).thenReturn(animal);
        Animal updatedAnimal = animalController.updateAnimal(1L, animal).getBody();
        assertEquals("Rex", updatedAnimal.getNome());
    }

    @Test
    void testDeleteAnimal() {
        Animal animal = new Animal();
        animal.setNome("Rex");
        animal.setIdade(5);
        animal.setRaca("Labrador");
        when(animalRepository.findById(anyLong())).thenReturn(Optional.of(animal));
        doNothing().when(animalRepository).delete(any(Animal.class));
        ResponseEntity<Void> response = animalController.deleteAnimal(1L);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testGetAllAgendamentos() {
        Agendamento agendamento = new Agendamento();
        agendamento.setAnimalId(1L);
        agendamento.setNome("Rex");
        agendamento.setDataAtendimento(LocalDateTime.now());
        when(agendamentoRepository.findAll()).thenReturn(Arrays.asList(agendamento));
        List<Agendamento> agendamentos = agendamentoController.getAllAgendamentos();
        assertEquals(1, agendamentos.size());
    }

    @Test
    void testCreateAgendamento() {
        Agendamento agendamento = new Agendamento();
        agendamento.setAnimalId(1L);
        agendamento.setNome("Rex");
        agendamento.setDataAtendimento(LocalDateTime.now());
        when(agendamentoRepository.save(any(Agendamento.class))).thenReturn(agendamento);
        ResponseEntity<Agendamento> response = agendamentoController.createAgendamento(agendamento);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("Rex", response.getBody().getNome());
    }

    @Test
    void testGetAgendamentoById() {
        Agendamento agendamento = new Agendamento();
        agendamento.setAnimalId(1L);
        agendamento.setNome("Rex");
        agendamento.setDataAtendimento(LocalDateTime.now());
        when(agendamentoRepository.findById(anyLong())).thenReturn(Optional.of(agendamento));
        ResponseEntity<Agendamento> response = agendamentoController.getAgendamentoById(1L);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("Rex", response.getBody().getNome());
    }

    @Test
    void testUpdateAgendamento() {
        Agendamento agendamento = new Agendamento();
        agendamento.setAnimalId(1L);
        agendamento.setNome("Rex");
        agendamento.setDataAtendimento(LocalDateTime.now());
        when(agendamentoRepository.findById(anyLong())).thenReturn(Optional.of(agendamento));
        when(agendamentoRepository.save(any(Agendamento.class))).thenReturn(agendamento);
        Agendamento updatedAgendamento = agendamentoController.updateAgendamento(1L, agendamento).getBody();
        assertEquals("Rex", updatedAgendamento.getNome());
    }

    @Test
    void testDeleteAgendamento() {
        Agendamento agendamento = new Agendamento();
        agendamento.setAnimalId(1L);
        agendamento.setNome("Rex");
        agendamento.setDataAtendimento(LocalDateTime.now());
        when(agendamentoRepository.findById(anyLong())).thenReturn(Optional.of(agendamento));
        doNothing().when(agendamentoRepository).delete(any(Agendamento.class));
        ResponseEntity<Void> response = agendamentoController.deleteAgendamento(1L);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testGetRandomCatFact() {
        CatFact catFact = new CatFact();
        catFact.setText("Cats sleep for 70% of their lives.");
        when(restTemplate.getForObject(anyString(), eq(CatFact[].class))).thenReturn(new CatFact[]{catFact});
        ResponseEntity<CatFact[]> response = catFactController.getRandomCatFact();
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("Cats sleep for 70% of their lives.", response.getBody()[0].getText());
    }
}
