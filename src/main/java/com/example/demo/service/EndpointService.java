package com.example.demo.service;

import com.example.demo.model.EndpointModel;

import java.util.List;

public interface EndpointService {

    List<EndpointModel> getAllData();

    EndpointModel getData(Long id);

    void updateData(EndpointModel endpointModel);
}
