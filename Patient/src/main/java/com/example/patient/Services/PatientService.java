package com.example.patient.Services;

import com.example.patient.Models.DossierMedical;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PatientService {

    private static final String PATIENT_SERVICE = "patientService";

    private final RestTemplate restTemplate;

    @Autowired
    public PatientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = PATIENT_SERVICE, fallbackMethod = "fallbackGetDossierMedicalForPatient")
    @Retry(name = PATIENT_SERVICE)
    public DossierMedical getDossierMedicalForPatient(int patientId) {
        String url = "http://dossier-medical-service/dossier-medical/getDossierMedicalForPatient?id=" + patientId;
        System.out.println("Calling URL: " + url);
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                DossierMedical.class
        ).getBody();
    }

    private DossierMedical fallbackGetDossierMedicalForPatient(int patientId, Throwable throwable) {
        System.err.println("Fallback for getDossierMedicalForPatient: " + throwable.getMessage());
        return new DossierMedical(patientId, "No diagnostic available", "No treatment available");
    }
}
