package com.zawisza.email.integration;

import com.zawisza.email.TestcontainersConfiguration;
import com.zawisza.email.model.EmailEntity;
import com.zawisza.email.model.EmailModel;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
class EmailRepositoryRestAssuredIntegrationTest extends TestcontainersConfiguration {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void shouldGetAllEmails() {
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
                    .as(new TypeRef<List<EmailModel>>() {});

        //then
        assertEquals(3, responseBody.size());
    }

    @Test
    void shouldGetOneEmail() {
        //given
        var id = 4;
        var expectedResult = new EmailModel()
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
                    .as(new TypeRef<EmailModel>() {});

        //then
        assertEquals(responseBody, expectedResult);
    }

    @Test
    void shouldUpdateOneEmail() {
        //given
        var id = 4L;
        var body = new EmailModel()
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
                "SELECT * FROM email_model WHERE id = ?",
                new BeanPropertyRowMapper<>(EmailEntity.class),
                id
        );

        assertEquals("Data updated", responseBody);
        assertEquals(body, EmailModel.toModel(result));
    }

    @Test
    void shouldNotUpdateEmail() {
        //given
        var id = 1L;
        var body = new EmailModel()
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
        assertEquals("Entity with id " + id + " not exist", responseBody);
    }

    @Test
    void shouldInsertOneEmail() {
        //given
        var body = new EmailModel()
                .setTemplate("new_email_template")
                .setEmailOrigin("new_email_origin")
                .setEmailDestination("new_email_destination");

        //when
        var responseBody = given()
                    .baseUri("http://localhost")
                    .port(serverPort)
                    .contentType(ContentType.JSON)
                .when()
                    .body(body)
                    .post("/insert")
                .then()
                    .statusCode(200)
                    .extract()
                    .body()
                    .asString();

        //then
        var result = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM email_model",
                Integer.class
        );

        assertEquals("Data inserted", responseBody);
        assertEquals(4, result);
    }
}
