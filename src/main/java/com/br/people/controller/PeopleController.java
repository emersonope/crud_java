package com.br.people.controller;

import com.br.people.model.PeopleModel;
import com.br.people.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PeopleService peopleService;

    @PostMapping
    public PeopleModel create(@RequestBody PeopleModel peopleModel) {
        return peopleService.create(peopleModel);
    }

    @GetMapping("/{cpf}")
    public PeopleModel findByCpf(@PathVariable String cpf) {
        return peopleService.findByCpf(cpf);
    }

    @PutMapping("/{cpf}")
    public PeopleModel update(@PathVariable String cpf, @RequestBody PeopleModel peopleModel) {
        return peopleService.update(cpf, peopleModel);
    }

    @DeleteMapping("/{cpf}")
    public void deleteByCpf(@PathVariable String cpf) {
        peopleService.deleteByCpf(cpf);
    }
}
