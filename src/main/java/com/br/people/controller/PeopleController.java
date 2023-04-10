package com.br.people.controller;

import com.br.people.model.PeopleModel;
import com.br.people.service.PeopleService;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PeopleService peopleService;

    @Timed(value = "people.create", description = "Tempo de resposta do endpoint de criação de pessoas")
    @PostMapping
    public PeopleModel create(@RequestBody PeopleModel peopleModel) {
        return peopleService.create(peopleModel);
    }

    @Timed(value = "people.findByCpf", description = "Tempo de resposta do endpoint de busca de pessoas por CPF")
    @GetMapping("/{cpf}")
    public PeopleModel findByCpf(@PathVariable String cpf) {
        return peopleService.findByCpf(cpf);
    }

    @Timed(value = "people.update", description = "Tempo de resposta do endpoint de atualização de pessoas")
    @PutMapping("/{cpf}")
    public PeopleModel update(@PathVariable String cpf, @RequestBody PeopleModel peopleModel) {
        return peopleService.update(cpf, peopleModel);
    }

    @Timed(value = "people.deleteByCpf", description = "Tempo de resposta do endpoint de exclusão de pessoas por CPF")
    @DeleteMapping("/{cpf}")
    public void deleteByCpf(@PathVariable String cpf) {
        peopleService.deleteByCpf(cpf);
    }
}
