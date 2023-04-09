package com.br.people.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Data
@Entity
@Table(name = "tb_people")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PeopleModel {

    @Id
    @CPF
    @NotNull
    @NotBlank
    private String cpf;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String telefone;
}
