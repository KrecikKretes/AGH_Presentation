package com.example.demo.integration;

import com.example.demo.model.EndpointEntity;
import com.example.demo.model.EndpointModel;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql(
        scripts = "/db/test-data.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class ProductRepositoryRestTemplateIntegrationTest extends TestcontainersConfiguration {

    private final JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory();
    private final RestTemplate restTemplate = new RestTemplate(requestFactory);

    private final String baseUri = "http://localhost:";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup(){
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public boolean hasError(@NonNull HttpStatusCode statusCode) {
                return false;
            }
        });
    }

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


    @Test
    void shouldUpdateOneProduct() {
        //given
        var id = 4L;
        var body = new EndpointModel()
                .setId(id)
                .setTemplate("email_template")
                .setEmailOrigin("email_origin")
                .setEmailDestination("email_destination");

        var endpoint = "/update";
        var uri = baseUri + serverPort + endpoint;

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var request = new HttpEntity<>(body, headers);

        //when
        var responseBody = restTemplate.exchange(
                URI.create(uri),
                HttpMethod.PATCH,
                request,
                String.class
        );

        //then
        var result = jdbcTemplate.queryForObject(
                "SELECT * FROM endpoint_model WHERE id = ?",
                new BeanPropertyRowMapper<>(EndpointEntity.class),
                id
        );

        assertEquals(responseBody.getStatusCode(), HttpStatusCode.valueOf(200));
        assertEquals("Data updated", responseBody.getBody());
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

        var endpoint = "/update";
        var uri = baseUri + serverPort + endpoint;

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var request = new HttpEntity<>(body, headers);

        //when
        var responseBody = restTemplate.exchange(
                URI.create(uri),
                HttpMethod.PATCH,
                request,
                String.class
        );

        //then
        assertEquals(HttpStatusCode.valueOf(400), responseBody.getStatusCode());
        assertEquals("Entity with id " + id + " not exist", responseBody.getBody());
    }


    @Test
    void shouldInsertOneProduct() {
        //given
        var body = new EndpointModel()
                .setTemplate("new_email_template")
                .setEmailOrigin("new_email_origin")
                .setEmailDestination("new_email_destination");

        var endpoint = "/insert";
        var uri = baseUri + serverPort + endpoint;

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var request = new HttpEntity<>(body, headers);

        //when
        var responseBody = restTemplate.exchange(
                URI.create(uri),
                HttpMethod.POST,
                request,
                String.class
        );

        //then
        var result = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM endpoint_model",
                Integer.class
        );

        assertEquals("Data inserted", responseBody.getBody());
        assertEquals(4, result);
    }
}
