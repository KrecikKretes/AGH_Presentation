package com.zawisza.email.repository;

import com.zawisza.email.model.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public interface EmailRepository extends JpaRepository<EmailEntity, Integer> {

    List<EmailEntity> getAllBy();

    EmailEntity findEndpointModelById(Long id);

}
