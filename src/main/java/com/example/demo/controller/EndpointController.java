package com.example.demo.controller;

import com.example.demo.model.EndpointModel;
import com.example.demo.service.EndpointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping
@Controller
@RequiredArgsConstructor
public class EndpointController {

    private final EndpointService endpointService;

    @GetMapping("/all")
    private ResponseEntity<List<EndpointModel>> getAllData() {
        return ResponseEntity.ok(endpointService.getAllData());
    }

    @GetMapping("/{id}")
    private ResponseEntity<EndpointModel> getData(@PathVariable Long id) {
       return ResponseEntity.ok(endpointService.getData(id));
    }

    @PatchMapping("/update")
    private ResponseEntity<String> updateData(@RequestBody EndpointModel endpointModel){
        endpointService.updateData(endpointModel);
        return ResponseEntity.ok("Data updated");
    }

    @PostMapping("/insert")
    private ResponseEntity<String> insertData(@RequestBody EndpointModel endpointModel) {
        endpointService.insertData(endpointModel);
        return ResponseEntity.ok("Data inserted");

    }
}
