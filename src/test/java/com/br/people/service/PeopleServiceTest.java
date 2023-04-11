package com.br.people.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.br.people.exeption.PeopleAlreadyExistsException;
import com.br.people.exeption.PeopleNotFoundException;
import com.br.people.model.PeopleModel;
import com.br.people.repository.PeopleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class PeopleServiceTest {

    @Mock
    private PeopleRepository peopleRepository;

    @InjectMocks
    private PeopleService peopleService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_create_a_valid_new_people() {
        // Given
        PeopleModel people = new PeopleModel("54909688005", "Joao", "11942142536");

        // When
        when(peopleRepository.existsById(people.getCpf())).thenReturn(false);
        when(peopleRepository.save(people)).thenReturn(people);

        PeopleModel result = peopleService.create(people);

        // Then
        Assertions.assertAll(
                () -> assertNotNull(result.getCpf()),
                () -> assertNotNull(result.getName()),
                () -> assertNotNull(result.getTelefone()),
                () -> assertEquals(people.getCpf(), result.getCpf()),
                () -> assertEquals(people.getName(), result.getName()),
                () -> assertEquals(people.getTelefone(), result.getTelefone())
        );

        verify(peopleRepository, times(1)).existsById(people.getCpf());
        verify(peopleRepository, times(1)).save(people);
    }

    @Test
    void should_return_an_exception_when_creating_people_that_exist() {
        // given
        PeopleModel existingPeople = new PeopleModel("54909688005", "Joao", "11942142536");
        Mockito.when(peopleRepository.existsById(existingPeople.getCpf())).thenReturn(true);

        // when
        Throwable exception = assertThrows(PeopleAlreadyExistsException.class, () -> {
            peopleService.create(existingPeople);
        });

        // then
        verify(peopleRepository, times(0)).save(existingPeople);
        assertEquals("Client with CPF 54909688005 Already exist.", exception.getMessage());
    }

    @Test
    public void should_findByCpf_an_existing_people() {
        // given
        String cpf = "54909688005";
        PeopleModel expected = new PeopleModel("54909688005", "Joao", "11942142536");

        // mockando o comportamento do repository
        when(peopleRepository.findById(cpf)).thenReturn(Optional.of(expected));

        // when
        PeopleModel result = peopleService.findByCpf(cpf);

        // then
        assertEquals(expected, result);
    }

    @Test
    public void should_throw_an_exception_peopleNotFoundException_if_people_not_found() {
        // given
        String cpf = "54909688005";

        // mockando o comportamento do repository
        when(peopleRepository.findById(cpf)).thenReturn(Optional.empty());

        // when, then
        assertThrows(PeopleNotFoundException.class, () -> peopleService.findByCpf(cpf));
    }

    @Test
    void should_update_people_if_exists() {
        // Dados de entrada
        String cpf = "54909688005";
        String nome = "João da Silva";
        String telefone = "11942142536";

        // Mock do repositório
        PeopleModel existingPeople = new PeopleModel(cpf, nome, telefone);
        Mockito.when(peopleRepository.findById(cpf)).thenReturn(Optional.of(existingPeople));
        Mockito.when(peopleRepository.save(Mockito.any())).thenReturn(existingPeople);

        // Executa o método a ser testado
        String novoNome = "João Silva";
        String novoTelefone = "11888888888";
        PeopleModel peopleModel = new PeopleModel(cpf, novoNome, novoTelefone);
        PeopleModel updatedPeople = peopleService.update(cpf, peopleModel);

        // Verificações
        assertEquals(novoNome, updatedPeople.getName());
        assertEquals(novoTelefone, updatedPeople.getTelefone());
        Mockito.verify(peopleRepository, Mockito.times(1)).findById(cpf);
        Mockito.verify(peopleRepository, Mockito.times(1)).save(existingPeople);
    }

    @Test
    public void should_delete_people_if_exists() {
        // given
        String cpf = "12345678900";
        PeopleModel existingCustomer = new PeopleModel(cpf, "John Doe", "123456789");
        Mockito.when(peopleRepository.findById(cpf)).thenReturn(Optional.of(existingCustomer));

        // when
        peopleService.deleteByCpf(cpf);

        // then
        Mockito.verify(peopleRepository, Mockito.times(1)).delete(existingCustomer);
    }

    @Test
    public void should_not_delete_and_throw_an_exception_peopleNotFoundException() {
        // given
        String cpf = "12345678900";
        Mockito.when(peopleRepository.findById(cpf)).thenReturn(Optional.empty());

        // when
        Executable executable = () -> peopleService.deleteByCpf(cpf);

        // then
        PeopleNotFoundException exception = assertThrows(PeopleNotFoundException.class, executable);
        assertEquals("Client not found with CPF: " + cpf, exception.getMessage());
        Mockito.verify(peopleRepository, Mockito.never()).delete(Mockito.any());
    }

}