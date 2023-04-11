package com.br.people.service;

import com.br.people.exeption.PeopleAlreadyExistsException;
import com.br.people.exeption.PeopleNotFoundException;
import com.br.people.model.PeopleModel;
import com.br.people.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PeopleService {

    @Autowired
    private PeopleRepository peopleRepository;

    public List<PeopleModel> listAll() {
        return peopleRepository.findAll();
    }
    public PeopleModel create(PeopleModel peopleModel) {
        if (peopleRepository.existsById(peopleModel.getCpf())) {
            throw new PeopleAlreadyExistsException("Client with CPF " + peopleModel.getCpf() + " Already exist.");
        }
        return peopleRepository.save(peopleModel);
    }

    public PeopleModel findByCpf(String cpf) {
        return peopleRepository.findById(cpf)
                .orElseThrow(() -> new PeopleNotFoundException("Client not found with CPF: " + cpf));
    }

    public PeopleModel update(String cpf, PeopleModel peopleModel) {
        PeopleModel existingPeopleModel = findByCpf(cpf);

        existingPeopleModel.setName(peopleModel.getName());
        existingPeopleModel.setTelefone(peopleModel.getTelefone());
        return peopleRepository.save(existingPeopleModel);
    }

    public void deleteByCpf(String cpf) {
        PeopleModel existingPeopleModel = findByCpf(cpf);
        peopleRepository.delete(existingPeopleModel);
    }
}
