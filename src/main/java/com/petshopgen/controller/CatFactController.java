package com.petshopgen.controller;

import com.petshopgen.model.CatFact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/cat-facts")
public class CatFactController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/random")
    public ResponseEntity<CatFact[]> getRandomCatFact() {
        String url = "https://cat-fact.herokuapp.com/facts/random?animal_type=cat&amount=1";
        CatFact[] catFacts = restTemplate.getForObject(url, CatFact[].class);
        return ResponseEntity.ok(catFacts);
    }
}
