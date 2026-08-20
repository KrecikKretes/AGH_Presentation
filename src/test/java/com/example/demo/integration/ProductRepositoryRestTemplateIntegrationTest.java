package com.example.demo.integration;

import com.example.demo.model.EndpointModel;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductRepositoryRestTemplateIntegrationTest extends TestcontainersConfiguration {

    /*
    TODO:
    1. Refaktor tej klasy. Wywalic rzeczy testcontainerowe do osobnej klasy - DONE
    1.5 Stworzyc te same testy z inna biblioteka
    2. Stworzyc unit testy z mockami
    3. Stworzyc jeszcze jeden test - DONE
    4. Dodac endpointy do edycji
    5. Przygotowac wariant, ze baza jest reuzywana - DONE
     */

    private final RestTemplate restTemplate = new RestTemplate();

    private final String baseUri = "http://localhost:";

    @Test
    void shouldGetAllProducts() {
        //given
        var endpoint = "/all";
        var uri = baseUri + serverPort + endpoint;

        //when
        var responseBody = restTemplate.exchange(
                URI.create(uri),
                HttpMethod.GET,
                null,
                List.class
        );

        //then
        assert responseBody.getBody() != null;
        assertEquals(3, responseBody.getBody().size());
    }

    @Test
    void shouldGetOneProducts() {
        //given
        var id = 4;
        var expectedResult = new EndpointModel()
                .setId(4L)
                .setTemplate("test-template1")
                .setEmailOrigin("test-origin1@test.com")
                .setEmailDestination("test-destination1@test.com");
        var endpoint = "/" + id;
        var uri = baseUri + serverPort + endpoint;

        //when
        var responseBody = restTemplate.exchange(
                URI.create(uri),
                HttpMethod.GET,
                null,
                EndpointModel.class
        );

        //then
        assertEquals(responseBody.getBody(), expectedResult);
    }
}
