package com.example.demo.integration;

import com.example.demo.model.EndpointModel;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductRepositoryRestAssuredIntegrationTest extends TestcontainersConfiguration {

    /*
    TODO:
    1. Refaktor tej klasy. Wywalic rzeczy testcontainerowe do osobnej klasy - DONE
    1.5 Stworzyc te same testy z inna biblioteka
    2. Stworzyc unit testy z mockami
    3. Stworzyc jeszcze jeden test - DONE
    4. Dodac endpointy do edycji
    5. Przygotowac wariant, ze baza jest reuzywana
    6. Jacoco/Sonatype?
     */

    @Test
    void shouldGetAllProducts() {
        //given

        //when
        var responseBody = given()
                    .baseUri("http://localhost")
                    .port(serverPort)
                .when()
                    .get("/all")
                .then()
                    .statusCode(200)
                    .extract()
                    .body()
                    .as(new TypeRef<List<EndpointModel>>() {});

        //then
        assertEquals(3, responseBody.size());
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

        //when
        var responseBody = given()
                    .baseUri("http://localhost")
                    .port(serverPort)
                .when()
                    .get("/" + id)
                .then()
                    .statusCode(200)
                    .extract()
                    .body()
                    .as(new TypeRef<EndpointModel>() {});

        //then
        assertEquals(responseBody, expectedResult);
    }
}
