package com.example.practicien.Services;

import com.example.practicien.Models.DossierMedical;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PracticienService {

    private static final String PRACTICIEN_SERVICE = "practicienService";

    private final RestTemplate restTemplate;

    @Autowired
    public PracticienService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = PRACTICIEN_SERVICE, fallbackMethod = "fallbackGetDossierMedicalForPatient")
    @Retry(name = PRACTICIEN_SERVICE)
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

    @CircuitBreaker(name = PRACTICIEN_SERVICE, fallbackMethod = "fallbackAddDossierMedical")
    @Retry(name = PRACTICIEN_SERVICE)
    public DossierMedical addDossierMedical(DossierMedical dossierMedical) {
        String url = "http://dossier-medical-service/dossier-medical/addDossierMedical";
        System.out.println("Calling URL: " + url);
        HttpEntity<DossierMedical> request = new HttpEntity<>(dossierMedical);
        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                DossierMedical.class
        ).getBody();
    }

    @CircuitBreaker(name = PRACTICIEN_SERVICE, fallbackMethod = "fallbackUpdateDossierMedical")
    @Retry(name = PRACTICIEN_SERVICE)
    public DossierMedical updateDossierMedical(int id, DossierMedical dossierMedical) {
        String url = "http://dossier-medical-service/dossier-medical/updateDossierMedical?id=" + id;
        System.out.println("Calling URL: " + url);
        HttpEntity<DossierMedical> request = new HttpEntity<>(dossierMedical);
        return restTemplate.exchange(
                url,
                HttpMethod.PUT,
                request,
                DossierMedical.class
        ).getBody();
    }

    @CircuitBreaker(name = PRACTICIEN_SERVICE, fallbackMethod = "fallbackDeleteDossierMedical")
    @Retry(name = PRACTICIEN_SERVICE)
    public void deleteDossierMedical(int id) {
        String url = "http://dossier-medical-service/dossier-medical/deleteDossierMedical?id=" + id;
        System.out.println("Calling URL: " + url);
        restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                null,
                Void.class
        );
    }

    private DossierMedical fallbackGetDossierMedicalForPatient(int patientId, Throwable throwable) {
        System.err.println("Fallback for getDossierMedicalForPatient: " + throwable.getMessage());
        return new DossierMedical(patientId, "No diagnostic available", "No treatment available");
    }

    private DossierMedical fallbackAddDossierMedical(DossierMedical dossierMedical, Throwable throwable) {
        System.err.println("Fallback for addDossierMedical: " + throwable.getMessage());
        return new DossierMedical(dossierMedical.getIdPatient(), "No diagnostic added", "No treatment added");
    }

    private DossierMedical fallbackUpdateDossierMedical(int id, DossierMedical dossierMedical, Throwable throwable) {
        System.err.println("Fallback for updateDossierMedical: " + throwable.getMessage());
        return new DossierMedical(dossierMedical.getIdPatient(), "Update failed", "Update failed");
    }

    private void fallbackDeleteDossierMedical(int id, Throwable throwable) {
        System.err.println("Fallback for deleteDossierMedical: " + throwable.getMessage());
    }
}
