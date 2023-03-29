package com.healthcareapp.backend.Service;

import com.healthcareapp.backend.Model.Encounter;
import com.healthcareapp.backend.Model.MedicalHistory;
import com.healthcareapp.backend.Model.Patient;
import com.healthcareapp.backend.Repository.MedicalHistoryRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MedicalHistoryService {
    private MedicalHistoryRepository medicalHistoryRepository;
    private PatientService patientService;

    public MedicalHistoryService(MedicalHistoryRepository medicalHistoryRepository, PatientService patientService) {
        this.medicalHistoryRepository = medicalHistoryRepository;
        this.patientService = patientService;
    }
    public List<MedicalHistory> getMedicalHistoryByPatientId(int patientId){
        Patient patient = patientService.getPatientById(patientId);
        List<MedicalHistory> medicalHistoryList = medicalHistoryRepository.findMedicalHistoriesByPatientId(patient);
        if(medicalHistoryList.size() == 0){
            throw new RuntimeException();
        }
        return medicalHistoryList;
    }
    public MedicalHistory addMedicalHistory(Patient patient, Encounter encounter){
        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.setPatientId(patient);
        medicalHistory.setEncounterId(encounter);
        medicalHistoryRepository.save(medicalHistory);
        return medicalHistory;
    }
    public MedicalHistory getMedicalHistoryByEncounter(Encounter encounterId){
        MedicalHistory medicalHistory = medicalHistoryRepository.findMedicalHistoryByEncounterId(encounterId);
        if(medicalHistory == null){
            throw new RuntimeException();
        }
        return medicalHistory;
    }
    public MedicalHistory updateMedicalHistory(String prescription, String symptoms, Encounter encounterId){
        MedicalHistory medicalHistory = getMedicalHistoryByEncounter(encounterId);
        try {
            medicalHistory.setPrescription(prescription);
            medicalHistory.setSymptoms(symptoms);
            medicalHistoryRepository.save(medicalHistory);
        }catch (Exception e){
            throw new RuntimeException();
        }
        return medicalHistory;
    }
}