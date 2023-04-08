package com.healthcareapp.backend.Controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.healthcareapp.backend.Model.Encounter;
import com.healthcareapp.backend.Model.MedicalHistory;
import com.healthcareapp.backend.Service.EncounterService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EncounterController {
    private EncounterService encounterService;

    public EncounterController(EncounterService encounterService) {
        this.encounterService = encounterService;
    }

    @PostMapping("addEncounters/{pid}/{docId}")
    public ResponseEntity<Encounter> addEncounter(@PathVariable int pid, @PathVariable int docId){
        Encounter savedEncounter;

        try{
            savedEncounter = encounterService.addEncounter(pid, docId);
        }
        catch (RuntimeException exception) {
            throw exception;
        }

        return ResponseEntity.of(Optional.of(savedEncounter));
    }

    @PostMapping("saveEncounter/{eid}")
    public MappingJacksonValue completeEncounter(@RequestBody MedicalHistory medicalHistory, @PathVariable int encounterId){
        MedicalHistory createdMedicalHistory;
        try {
            createdMedicalHistory = encounterService.saveEncounter(medicalHistory.getPrescription(), medicalHistory.getSymptoms(), encounterId);
        }catch (RuntimeException exception){
            throw exception;
        }

        MappingJacksonValue mappingJacksonValue= new MappingJacksonValue(createdMedicalHistory);
        SimpleBeanPropertyFilter filter= SimpleBeanPropertyFilter.filterOutAllExcept("medicalHistoryId", "patient", "symptoms", "prescription");
        FilterProvider filters= new SimpleFilterProvider().addFilter("MedicalHistoryFilter", filter);

        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;
    }

}
