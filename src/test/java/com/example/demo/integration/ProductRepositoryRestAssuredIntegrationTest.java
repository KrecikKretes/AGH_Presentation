package com.example.demo.integration;

import com.example.demo.model.EndpointEntity;
import com.example.demo.model.EndpointModel;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql(
        scripts = "/db/test-data.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class ProductRepositoryRestAssuredIntegrationTest extends TestcontainersConfiguration {

    /*
    TODO:
    1. Refaktor tej klasy. Wywalic rzeczy testcontainerowe do osobnej klasy - DONE
    1.5 Stworzyc te same testy z inna biblioteka - DONE
    2. Stworzyc unit testy z mockami - DONE
    3. Stworzyc jeszcze jeden test - DONE
    4. Dodac endpointy do edycji - DONE
    5. Przygotowac wariant, ze baza jest reuzywana - DONE
    6. Jacoco/Sonatype?
     */

    @Autowired
    private JdbcTemplate jdbcTemplate;

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

    @Test
    void shouldUpdateOneProduct() {
        //given
        var id = 4L;
        var body = new EndpointModel()
                .setId(id)
                .setTemplate("email_template")
                .setEmailOrigin("email_origin")
                .setEmailDestination("email_destination");

        //when
        var responseBody = given()
                    .baseUri("http://localhost")
                    .port(serverPort)
                    .contentType(ContentType.JSON)
                .when()
                    .body(body)
                    .patch("/update")
                .then()
                    .statusCode(200)
                    .extract()
                    .body()
                    .asString();

        //then
        var result = jdbcTemplate.queryForObject(
                "SELECT * FROM endpoint_model WHERE id = ?",
                new BeanPropertyRowMapper<>(EndpointEntity.class),
                id
        );

        assertEquals(responseBody, "Data updated");
        assertEquals(body, EndpointModel.toModel(result));
    }

    @Test
    void shouldNotUpdateProduct() {
        //given
        var id = 1L;
        var body = new EndpointModel()
                .setId(id)
                .setTemplate("email_template")
                .setEmailOrigin("email_origin")
                .setEmailDestination("email_destination");

        //when
        var responseBody = given()
                    .baseUri("http://localhost")
                    .port(serverPort)
                    .contentType(ContentType.JSON)
                .when()
                    .body(body)
                    .patch("/update")
                .then()
                    .statusCode(400)
                    .extract()
                    .body()
                    .asString();

        //then
        assertEquals(responseBody, "Entity with id " + id + " not exist");
    }
}
