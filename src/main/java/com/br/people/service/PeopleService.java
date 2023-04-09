package com.br.people.service;

import com.br.people.exeption.PeopleNotFoundException;
import com.br.people.model.PeopleModel;
import com.br.people.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeopleService {

    @Autowired
    private PeopleRepository peopleRepository;

    public PeopleModel create(PeopleModel peopleModel) {
        return peopleRepository.save(peopleModel);
    }

    public PeopleModel findByCpf(String cpf) {
        return peopleRepository.findById(cpf)
                .orElseThrow(() -> new PeopleNotFoundException("Customer not found with CPF: " + cpf));
    }

    public PeopleModel update(String cpf, PeopleModel peopleModel) {
        PeopleModel existingCustomer = findByCpf(cpf);
        existingCustomer.setNome(peopleModel.getNome());
        existingCustomer.setTelefone(peopleModel.getTelefone());
        return peopleRepository.save(existingCustomer);
    }

    public void deleteByCpf(String cpf) {
        PeopleModel existingCustomer = findByCpf(cpf);
        peopleRepository.delete(existingCustomer);
    }
}
