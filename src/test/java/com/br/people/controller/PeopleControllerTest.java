package com.br.people.controller;

import com.br.people.exeption.PeopleNotFoundException;
import com.br.people.model.PeopleModel;
import com.br.people.service.PeopleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PeopleControllerTest {

    @Mock
    private PeopleService peopleService;

    @InjectMocks
    private PeopleController peopleController;

    @Test
    public void should_create_a_new_people_with_sucess() {
        // Given
        PeopleModel peopleModel = new PeopleModel("54909688005", "Joao", "11942142536");
        when(peopleService.create(peopleModel)).thenReturn(peopleModel);

        // When
        PeopleModel result = peopleController.create(peopleModel);

        // Then
        Assertions.assertAll(
                () -> assertNotNull(result.getCpf()),
                () -> assertNotNull(result.getName()),
                () -> assertNotNull(result.getTelefone()),
                () -> assertEquals(peopleModel.getCpf(), result.getCpf()),
                () -> assertEquals(peopleModel.getName(), result.getName()),
                () -> assertEquals(peopleModel.getTelefone(), result.getTelefone())
        );

        verify(peopleService).create(peopleModel);
    }

    @Test
    void should_return_peple_by_cpf() {
        // Given
        String cpf = "54909688005";
        PeopleModel peopleModel = new PeopleModel(cpf, "Joao", "11942142536");
        when(peopleService.findByCpf(cpf)).thenReturn(peopleModel);

        // When
        PeopleModel result = peopleController.findByCpf(cpf);

        // Then
        assertEquals(cpf, result.getCpf());
        assertEquals("Joao", result.getName());
        assertEquals("11942142536", result.getTelefone());
    }

    @Test
    void findByCpf_should_Throw_PeopleNotFoundException() {
        // Given
        String cpf = "54909688005";
        when(peopleService.findByCpf(cpf)).thenThrow(new PeopleNotFoundException("Customer not found with CPF: " + cpf));

        // When / Then
        assertThrows(PeopleNotFoundException.class, () -> peopleController.findByCpf(cpf));
    }

    @Test
    public void should_create_an_object_when_update_from_service_called_correctly_and_call_update_from_controller_to_verify_if_it_is_equal() {
        String cpf = "54909688005";
        PeopleModel peopleModel = new PeopleModel(cpf, "João", "11942142536");
        when(peopleService.update(eq(cpf), any(PeopleModel.class))).thenReturn(peopleModel);

        PeopleModel result = peopleController.update(cpf, peopleModel);

        assertEquals(peopleModel, result);
        //verifica se o metodo update foi chamado exatamente uma vez
        verify(peopleService, times(1)).update(eq(cpf), any(PeopleModel.class));
    }

    @Test
    void should_delete_existing_people() {
        String cpf = "54909688005";

        doNothing().when(peopleService).deleteByCpf(cpf);

        peopleController.deleteByCpf(cpf);

        verify(peopleService, times(1)).deleteByCpf(cpf);
    }

    @Test
    void should_throw_peopleNotFoundException() {
        String cpf = "11111111111";

        doThrow(new PeopleNotFoundException("Customer not found with CPF: " + cpf))
                .when(peopleService).deleteByCpf(cpf);

        assertThrows(PeopleNotFoundException.class, () -> {
            peopleController.deleteByCpf(cpf);
        });

        verify(peopleService, times(1)).deleteByCpf(cpf);
    }
}
