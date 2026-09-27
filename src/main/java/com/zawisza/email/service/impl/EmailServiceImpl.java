package com.zawisza.email.service.impl;

import com.zawisza.email.model.EmailModel;
import com.zawisza.email.repository.EmailRepository;
import com.zawisza.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailRepository emailRepository;

    @Override
    public List<EmailModel> getAllData() {
        return emailRepository.getAllBy()
                .stream()
                .map(EmailModel::toModel)
                .toList();
    }

    @Override
    public EmailModel getData(Long id) {
        return EmailModel.toModel(emailRepository.findEndpointModelById(id));
    }

    @Override
    public void updateData(EmailModel emailModel) {
        var entity = emailRepository.findEndpointModelById(emailModel.getId());
        if (entity == null) {
            throw new NoSuchElementException("Entity with id " + emailModel.getId() + " not exist");
        }
        entity.setTemplate(emailModel.getTemplate());
        entity.setEmailOrigin(emailModel.getEmailOrigin());
        entity.setEmailDestination(emailModel.getEmailDestination());
        emailRepository.save(entity);
    }

    @Override
    public void insertData(EmailModel emailModel) {
        emailRepository.save(emailModel.toEntity());
    }
}
