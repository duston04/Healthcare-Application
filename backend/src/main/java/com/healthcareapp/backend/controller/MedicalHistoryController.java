package com.healthcareapp.backend.controller;

import com.healthcareapp.backend.entities.MedicalHistory;
import com.healthcareapp.backend.services.MedicalHistoryServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class MedicalHistoryController {

    private MedicalHistoryServices medicalHistoryServices;

//    @GetMapping("getMedicalHistory/{pid}")
//    public ResponseEntity<List<MedicalHistory>> getMedicalHistory(@PathVariable int pid){
//
//    }

}