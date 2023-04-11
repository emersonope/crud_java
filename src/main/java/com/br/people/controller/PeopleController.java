package com.br.people.controller;

import com.br.people.model.PeopleModel;
import com.br.people.service.PeopleService;
import io.micrometer.core.annotation.Timed;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PeopleService peopleService;
    private final static Logger logger = LogManager.getLogger();

    @GetMapping("/getAll")
    public List<PeopleModel> list() {
        logger.info("Listing all clients!");
        return peopleService.listAll();
    }

    @Timed(value = "people.create", description = "Client creation endpoint response time")
    @PostMapping
    public PeopleModel create(@RequestBody PeopleModel peopleModel) {
        logger.info("Creating client with CPF: {}", peopleModel.getCpf());
        return peopleService.create(peopleModel);
    }

    @Timed(value = "people.findByCpf", description = "Response time of the client search endpoint by CPF")
    @GetMapping("/{cpf}")
    public PeopleModel findByCpf(@PathVariable String cpf) {
        logger.info("Searching for CPF: {}", cpf);
        return peopleService.findByCpf(cpf);
    }

    @Timed(value = "people.update", description = "Client update endpoint response time")
    @PutMapping("/{cpf}")
    public PeopleModel update(@PathVariable String cpf, @RequestBody PeopleModel peopleModel) {
        logger.info("Updating by CPF {}: {}", cpf);
        return peopleService.update(cpf, peopleModel);
    }

    @Timed(value = "people.deleteByCpf", description = "Client deletion endpoint response time per CPF")
    @DeleteMapping("/{cpf}")
    public void deleteByCpf(@PathVariable String cpf) {
        logger.info("Deleting by CPF: {}", cpf);
        peopleService.deleteByCpf(cpf);
    }
}
