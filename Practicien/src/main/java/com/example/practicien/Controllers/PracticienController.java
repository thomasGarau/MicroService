package com.example.practicien.Controllers;

import com.example.practicien.Models.Practicien;
import com.example.practicien.Models.Specialite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.practicien.Models.DossierMedical;
import com.example.practicien.Services.PracticienService;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/practicien")
public class PracticienController {

    private final PracticienService practicienService;

    private List<Specialite> specialites = new ArrayList<>(asList(
            new Specialite("Dentiste"),
            new Specialite("Ophtalmologue"),
            new Specialite("Généraliste"),
            new Specialite("Kinésithérapeute"),
            new Specialite("Dermatologue"),
            new Specialite("Gynécologue"),
            new Specialite("Pédiatre")
    ));
    private List<Practicien> practiciens = new ArrayList<Practicien>();

    @Autowired
    public PracticienController(PracticienService practicienService) {
        this.practicienService = practicienService;
    }

    @GetMapping("/getPracticien")
    public Practicien getPatient(@RequestParam int id) {
        return practiciens
                .stream()
                .filter(practicien -> practicien.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @GetMapping("/getAllSpecialite")
    public List<Specialite> getAllSpecialite() {
        return specialites;
    }

    @GetMapping("/getAllPracticienForSpecialite")
    public List<Practicien> getAllPracticienForSpecialite(@RequestParam String specialite) {
        return practiciens
                .stream()
                .filter(practicien -> practicien.getSpecialite().getNom().equals(specialite))
                .collect(Collectors.toList());
    }

    @PostMapping("/addPracticien")
    public Practicien addPracticien(@RequestBody Practicien practicien) {
        Specialite specialite = specialites
                .stream()
                .filter(s -> s.getNom().equals(practicien.getSpecialite().getNom()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "Spécialité invalide : " + practicien.getSpecialite().getNom() + ". Veuillez choisir parmi : " + specialites.stream().map(Specialite::getNom).collect(Collectors.joining(", "))
                ));

        practicien.setSpecialite(specialite);

        practiciens.add(practicien);

        return practicien;
    }


    @DeleteMapping("/deletePracticien")
    public void deletePracticien(@RequestParam int id) {
        practiciens.removeIf(practicien -> practicien.getId() == id);
    }

    @PutMapping("/updatePracticien")
    public Practicien updatePracticien(
            @RequestParam int id,
            @RequestBody Practicien practicien
    ) throws IllegalAccessException {
        Practicien practicienToUpdate = practiciens
                .stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Practicien introuvable avec l'id : " + id));

        if (practicien.getSpecialite() != null) {
            Specialite specialite = specialites
                    .stream()
                    .filter(s -> s.getNom().equals(practicien.getSpecialite().getNom()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException(
                            "Spécialité invalide : " + practicien.getSpecialite().getNom() +
                                    ". Veuillez choisir parmi : " +
                                    specialites.stream().map(Specialite::getNom).collect(Collectors.joining(", "))
                    ));

            practicien.setSpecialite(specialite);
        }

        updateFieldsDynamically(practicienToUpdate, practicien);

        return practicienToUpdate;
    }


    private void updateFieldsDynamically(Object target, Object source) throws IllegalAccessException {
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);

            // Ignorer le champ "id"
            if (field.getName().equals("id")) {
                continue;
            }

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

    // Routes pour interagir avec Dossier Medical

    @GetMapping("/getDossierMedical")
    public DossierMedical getDossierMedical(@RequestParam int patientId) {
        return practicienService.getDossierMedicalForPatient(patientId);
    }

    @PostMapping("/addDossierMedical")
    public DossierMedical addDossierMedical(@RequestBody DossierMedical dossierMedical) {
        return practicienService.addDossierMedical(dossierMedical);
    }

    @PutMapping("/updateDossierMedical")
    public DossierMedical updateDossierMedical(@RequestParam int id, @RequestBody DossierMedical dossierMedical) {
        return practicienService.updateDossierMedical(id, dossierMedical);
    }

    @DeleteMapping("/deleteDossierMedical")
    public void deleteDossierMedical(@RequestParam int id) {
        practicienService.deleteDossierMedical(id);
    }

}
