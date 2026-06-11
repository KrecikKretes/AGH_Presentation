package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "endpoint_model")
@Getter
@Setter
public class EndpointEntity {

    @Id
    Long id;

    String template;

    String emailOrigin;

    String emailDestination;
}
