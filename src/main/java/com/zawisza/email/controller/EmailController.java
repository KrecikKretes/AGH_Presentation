package com.zawisza.email.controller;

import com.zawisza.email.model.EmailModel;
import com.zawisza.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping
@Controller
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/all")
    private ResponseEntity<List<EmailModel>> getAllData() {
        return ResponseEntity.ok(emailService.getAllData());
    }

    @GetMapping("/{id}")
    private ResponseEntity<EmailModel> getData(@PathVariable Long id) {
       return ResponseEntity.ok(emailService.getData(id));
    }

    @PatchMapping("/update")
    private ResponseEntity<String> updateData(@RequestBody EmailModel emailModel){
        emailService.updateData(emailModel);
        return ResponseEntity.ok("Data updated");
    }

    @PostMapping("/insert")
    private ResponseEntity<String> insertData(@RequestBody EmailModel emailModel) {
        emailService.insertData(emailModel);
        return ResponseEntity.ok("Data inserted");

    }
}
