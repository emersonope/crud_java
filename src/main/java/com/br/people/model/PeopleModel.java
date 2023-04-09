package com.br.people.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.br.CPF;

@Data
@Entity
@Table(name = "customer")
public class PeopleModel {

    @Id
    @CPF
    @NonNull
    @NotBlank
    private String cpf;

    @NotNull
    @NotBlank
    private String nome;

    @NotNull
    private String telefone;

    public PeopleModel (String cpf, String nome, String telefone) {
        this.cpf = cpf;
        this.nome = nome;
        this.telefone = telefone;
    }
}
