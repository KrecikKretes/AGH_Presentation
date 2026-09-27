package com.example.demo.unit;

import com.example.demo.model.EndpointModel;
import com.example.demo.repository.EndpointRepository;
import com.example.demo.service.impl.EndpointServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EndpointServiceTest {

    @Mock
    private EndpointRepository repository;

    @InjectMocks
    private EndpointServiceImpl service;

    @Test
    void getAllData() {
        //given
        var model = new EndpointModel()
                .setId(1L)
                .setTemplate("template")
                .setEmailOrigin("emailOrigin")
                .setEmailDestination("emailDestination");
        var list = List.of(
                model.toEntity()
        );
        when(repository.getAllBy()).thenReturn(list);

        //when
        var data = service.getAllData();
        //then
        assertEquals(data, List.of(model));
    }

    @Test
    void getDataById() {
        //given
        var model = new EndpointModel()
                .setId(1L)
                .setTemplate("template")
                .setEmailOrigin("emailOrigin")
                .setEmailDestination("emailDestination");
        when(repository.findEndpointModelById(1L)).thenReturn(model.toEntity());

        //when
        var data = service.getData(1L);
        //then
        assertEquals(data, model);
    }

    @Test
    void updateData() {
        //given
        var model = new EndpointModel()
                .setId(1L)
                .setTemplate("template")
                .setEmailOrigin("emailOrigin")
                .setEmailDestination("emailDestination");
        var newModel = new EndpointModel()
                .setId(1L)
                .setTemplate("new_template")
                .setEmailOrigin("new_emailOrigin")
                .setEmailDestination("new_emailDestination");
        when(repository.findEndpointModelById(1L)).thenReturn(model.toEntity());

        //when
        service.updateData(newModel);
        //then
        verify(repository).save(argThat(entity ->
                entity.getId().equals(1L)
                        && entity.getTemplate().equals("new_template")
                        && entity.getEmailOrigin().equals("new_emailOrigin")
                        && entity.getEmailDestination().equals("new_emailDestination")
        ));
    }

    @Test
    void notUpdateDataByEntityWithTheSameIdExist() {
        //given
        var newModel = new EndpointModel()
                .setId(1L)
                .setTemplate("new_template")
                .setEmailOrigin("new_emailOrigin")
                .setEmailDestination("new_emailDestination");
        when(repository.findEndpointModelById(1L)).thenReturn(null);

        //when + then
        assertThrows(
                NoSuchElementException.class,
                () -> service.updateData(newModel)
        );
    }

    @Test
    void insertData() {
        //given
        var newModel = new EndpointModel()
                .setId(1L)
                .setTemplate("new_template")
                .setEmailOrigin("new_emailOrigin")
                .setEmailDestination("new_emailDestination");

        //when
        service.insertData(newModel);

        // then
        verify(repository).save(argThat(entity ->
                entity.getId().equals(1L)
                        && entity.getTemplate().equals("new_template")
                        && entity.getEmailOrigin().equals("new_emailOrigin")
                        && entity.getEmailDestination().equals("new_emailDestination")
        ));
    }
}