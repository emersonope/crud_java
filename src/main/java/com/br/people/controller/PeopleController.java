package com.br.people.controller;

import com.br.people.model.PeopleModel;
import com.br.people.service.PeopleService;
import io.micrometer.core.annotation.Timed;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PeopleService peopleService;
    private final static Logger logger = LogManager.getLogger();

    @Timed(value = "people.create", description = "Tempo de resposta do endpoint de criação de pessoas")
    @PostMapping
    public PeopleModel create(@RequestBody PeopleModel peopleModel) {
        logger.info("Criando pessoa: {}", peopleModel);
        return peopleService.create(peopleModel);
    }

    @Timed(value = "people.findByCpf", description = "Tempo de resposta do endpoint de busca de pessoas por CPF")
    @GetMapping("/{cpf}")
    public PeopleModel findByCpf(@PathVariable String cpf) {
        logger.info("Buscando pessoa por CPF: {}", cpf);
        return peopleService.findByCpf(cpf);
    }

    @Timed(value = "people.update", description = "Tempo de resposta do endpoint de atualização de pessoas")
    @PutMapping("/{cpf}")
    public PeopleModel update(@PathVariable String cpf, @RequestBody PeopleModel peopleModel) {
        logger.info("Atualizando pessoa com CPF {}: {}", cpf, peopleModel);
        return peopleService.update(cpf, peopleModel);
    }

    @Timed(value = "people.deleteByCpf", description = "Tempo de resposta do endpoint de exclusão de pessoas por CPF")
    @DeleteMapping("/{cpf}")
    public void deleteByCpf(@PathVariable String cpf) {
        logger.info("Excluindo pessoa com CPF: {}", cpf);
        peopleService.deleteByCpf(cpf);
    }
}
