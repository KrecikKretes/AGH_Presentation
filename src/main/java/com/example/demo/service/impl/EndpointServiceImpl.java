package com.example.demo.service.impl;

import com.example.demo.model.EndpointModel;
import com.example.demo.repository.EndpointRepository;
import com.example.demo.service.EndpointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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

    @Override
    public void updateData(EndpointModel endpointModel) {
        var entity = endpointRepository.findEndpointModelById(endpointModel.getId());
        if (entity == null) {
            throw new NoSuchElementException("Entity with id " + endpointModel.getId() + " not exist");
        }
        entity.setTemplate(endpointModel.getTemplate());
        entity.setEmailOrigin(endpointModel.getEmailOrigin());
        entity.setEmailDestination(endpointModel.getEmailDestination());
        endpointRepository.save(entity);
    }

    @Override
    public void insertData(EndpointModel endpointModel) {
        endpointRepository.save(endpointModel.toEntity());
    }
}
