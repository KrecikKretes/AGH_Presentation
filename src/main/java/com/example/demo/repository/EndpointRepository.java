package com.example.demo.repository;

import com.example.demo.model.EndpointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public interface EndpointRepository extends JpaRepository<EndpointEntity, Integer> {

    List<EndpointEntity> getAllBy();

    EndpointEntity findEndpointModelById(Long id);

}
