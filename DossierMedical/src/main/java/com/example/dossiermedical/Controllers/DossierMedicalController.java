package com.example.dossiermedical.Controllers;

import com.example.dossiermedical.Models.DossierMedical;
import com.example.dossiermedical.Services.DossierMedicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/dossier-medical")
public class DossierMedicalController {

    private final DossierMedicalService dossierMedicalService;
    private static List<DossierMedical> dossierMedicalList = new ArrayList<>();

    @Autowired
    public DossierMedicalController(DossierMedicalService dossierMedicalService) {
        this.dossierMedicalService = dossierMedicalService;
    }

    @GetMapping("/getDossierMedicalForPatient")
    public DossierMedical getDossierMedicalForPatient(@RequestParam int id) {
        return dossierMedicalList
                .stream()
                .filter(dossierMedical -> dossierMedical.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @PostMapping("/addDossierMedical")
    public DossierMedical addDossierMedical(@RequestBody DossierMedical dossierMedical) {
        dossierMedicalList.add(dossierMedical);
        return dossierMedical;
    }

    @DeleteMapping("/deleteDossierMedical")
    public void deleteDossierMedical(@RequestParam int id) {
        dossierMedicalList.removeIf(dossierMedical -> dossierMedical.getId() == id);
    }

    @PutMapping("/updateDossierMedical")
    public DossierMedical updateDossierMedical(@RequestParam int id, @RequestBody DossierMedical dossierMedical) {
        DossierMedical dossierMedicalToUpdate = dossierMedicalList
                .stream()
                .filter(d -> d.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("DossierMedical introuvable avec l'id : " + id));

        dossierMedicalToUpdate.setIdPatient(dossierMedical.getIdPatient());
        dossierMedicalToUpdate.setDiagnostic(dossierMedical.getDiagnostic());
        dossierMedicalToUpdate.setTraitement(dossierMedical.getTraitement());

        return dossierMedicalToUpdate;
    }
}
