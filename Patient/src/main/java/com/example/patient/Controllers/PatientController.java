package com.example.patient.Controllers;

import com.example.patient.Request.PatientRequest;
import org.springframework.web.bind.annotation.*;
import com.example.patient.Models.Patient;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private static List<Patient> listPatient = new ArrayList<>();

    @GetMapping("/getPatient")
    public Patient getPatient(@RequestParam int id) {
        return listPatient
                .stream()
                .filter(patient -> patient.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @PostMapping("/addPatient")
    public Patient addPatient(
            @RequestBody PatientRequest request
    ) {
        Patient patient = new Patient(request.getNom(), request.getPrenom(), request.getAge());
        listPatient.add(patient);
        return patient;
    }

    @DeleteMapping("/deletePatient")
    public void deletePatient(@RequestParam int id) {
        listPatient.removeIf(patient -> patient.getId() == id);
    }

    @PutMapping("/updatePatient")
    public Patient updatePatient(
            @RequestParam int id,
            @RequestBody PatientRequest request
    ) throws IllegalAccessException {
        Patient patient = listPatient
                .stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Patient introuvable avec l'id : " + id));

        updateFieldsDynamically(patient, request);

        return patient;
    }

    private void updateFieldsDynamically(Object target, Object source) throws IllegalAccessException {
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);

            Object value = field.get(source);
            if (value != null) {
                Field targetField;
                try {
                    targetField = target.getClass().getDeclaredField(field.getName());
                    targetField.setAccessible(true);
                    targetField.set(target, value);
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException("Champ introuvable : " + field.getName());
                }
            }
        }
    }
}

