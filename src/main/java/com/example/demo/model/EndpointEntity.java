package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "endpoint_model")
@Getter
@Setter
public class EndpointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String template;

    String emailOrigin;

    String emailDestination;
}
