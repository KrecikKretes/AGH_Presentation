package com.example.demo.service.impl;

import com.example.demo.model.EndpointModel;
import com.example.demo.repository.EndpointRepository;
import com.example.demo.service.EndpointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EndpointServiceImpl implements EndpointService {

    private final EndpointRepository endpointRepository;

    @Override
    public List<EndpointModel> getAllData() {
        return endpointRepository.getAllBy()
                .stream()
                .map(EndpointModel::toModel)
                .toList();
    }

    @Override
    public EndpointModel getData(Long id) {
        return EndpointModel.toModel(endpointRepository.findEndpointModelById(id));
    }
}
