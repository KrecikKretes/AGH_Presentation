package com.zawisza.email.service;

import com.zawisza.email.model.EmailModel;

import java.util.List;

public interface EmailService {

    List<EmailModel> getAllData();

    EmailModel getData(Long id);

    void updateData(EmailModel emailModel);

    void insertData(EmailModel emailModel);

}
