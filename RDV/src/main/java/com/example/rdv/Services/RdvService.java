package com.example.rdv.Services;

import com.example.rdv.Models.Patient;
import com.example.rdv.Models.Practicien;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RdvService {

    private static final String RDV_SERVICE = "rdvService";

    private final RestTemplate restTemplate;

    @Autowired
    public RdvService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = RDV_SERVICE, fallbackMethod = "fallbackGetPatientById")
    @Retry(name = RDV_SERVICE)
    public Patient getPatientById(int id) {
        String url = "http://patient-service/patient/getPatient?id=" + id;
        System.out.println("Calling URL: " + url);
        return restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                Patient.class).getBody();
    }

    @CircuitBreaker(name = RDV_SERVICE, fallbackMethod = "fallbackGetPracticienById")
    @Retry(name = RDV_SERVICE)
    public Practicien getPracticienById(int id) {
        String url = "http://practicien-service/practicien/getPracticien?id=" + id;
        System.out.println("Calling URL: " + url);
        return restTemplate.getForObject(url, Practicien.class);
    }

    @CircuitBreaker(name= RDV_SERVICE, fallbackMethod = "fallbackGetCalendar")
    @Retry(name = RDV_SERVICE)
    public String getCalendar(int id) {
        String url = "todo" + id;
        System.out.println("Calling URL: " + url);
        return restTemplate.getForObject(url, String.class);
    }

    private Patient fallbackGetPatientById(int id, Throwable throwable) {
        System.err.println("Fallback for getPatientById: " + throwable.getMessage());
        return new Patient(id, "Unknown", "Fallback", 0);
    }

    private Practicien fallbackGetPracticienById(int id, Throwable throwable) {
        System.err.println("Fallback for getPracticienById: " + throwable.getMessage());
        return new Practicien(id, "Unknown", "Fallback", null);
    }

    private String fallbackGetCalendar(int id, Throwable throwable) {
        System.err.println("Fallback for getCalendar: " + throwable.getMessage());
        return "Unknown";
    }
}
