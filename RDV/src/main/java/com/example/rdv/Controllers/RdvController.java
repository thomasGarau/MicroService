package com.example.rdv.Controllers;

import com.example.rdv.Models.Patient;
import com.example.rdv.Models.Practicien;
import com.example.rdv.Models.RDV;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import com.example.rdv.Services.RdvService;
import com.example.rdv.Requests.CreateRdvRequest;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/rdv")
public class RdvController {

    private final RdvService rdvService;

    private List <RDV> rdvList = new ArrayList<RDV>();

    @Autowired
    public RdvController(RdvService rdvService) {
        this.rdvService = rdvService;
    }

    @GetMapping("/getRdv")
    public RDV getRdv(@RequestParam int id) {
        return rdvList
                .stream()
                .filter(rdv -> rdv.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @GetMapping("/getAllRdvForPatient")
    public List<RDV> getAllRdvForPatient(@RequestParam int id) {
        // Récupérer le patient
        Patient patient = rdvService.getPatientById(id);

        List<RDV> patientRdv = new ArrayList<>();

        for (RDV rdv : rdvList) {
            if (rdv.getPatient().getId() == id) {
                patientRdv.add(rdv);
            }
        }

        return patientRdv;
    }


    @GetMapping("/getAllRdvForPracticien")
    public List<RDV> getAllRdvForPracticien(@RequestParam int id) {
        // Récupérer le praticien
        Practicien practicien = rdvService.getPracticienById(id);

        List<RDV> practicienRdv = new ArrayList<>();

        for (RDV rdv : rdvList) {
            if (rdv.getPracticien().getId() == id) {
                practicienRdv.add(rdv);
            }
        }

        return practicienRdv;
    }

    @PostMapping("/addRdv")
    public RDV addRdv(@RequestBody CreateRdvRequest request) {

        // Récupérer le patient à partir de son ID
        Patient patient = rdvService.getPatientById(request.getPatientId());
        if (patient == null) {
            throw new RuntimeException("Patient introuvable avec l'ID : " + request.getPatientId());
        }

        // Récupérer le praticien à partir de son ID
        Practicien practicien = rdvService.getPracticienById(request.getPracticienId());
        if (practicien == null) {
            throw new RuntimeException("Practicien introuvable avec l'ID : " + request.getPracticienId());
        }

        // Créer un nouvel objet RDV
        RDV rdv = new RDV();
        rdv.setDate(request.getDate());
        rdv.setHeure(request.getHeure());
        rdv.setPatient(patient);
        rdv.setPracticien(practicien);
        rdv.setMotif(request.getMotif());
        rdv.setEtat(request.getEtat());

        // Ajouter le RDV à la liste
        rdvList.add(rdv);

        return rdv;
    }

    @DeleteMapping("/deleteRdv")
    public void deleteRdv(@RequestParam int id) {
        rdvList.removeIf(rdv -> rdv.getId() == id);
    }

    @PutMapping("/updateRdvHoraire")
    public RDV updateRdvHoraire(@RequestParam int id, @RequestBody Map<String, String> updateData) {
        // Trouver le rendez-vous par ID
        RDV rdv = rdvList.stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Rendez-vous introuvable avec l'ID : " + id));

        // Mettre à jour la date et l'heure si elles sont fournies
        if (updateData.containsKey("date")) {
            rdv.setDate(updateData.get("date"));
        }
        if (updateData.containsKey("heure")) {
            rdv.setHeure(updateData.get("heure"));
        }

        return rdv;
    }

    @PutMapping("/updateRdvEtat")
    public RDV updateRdvEtat(@RequestParam int id, @RequestBody Map<String, String> updateData) {
        // Trouver le rendez-vous par ID
        RDV rdv = rdvList.stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Rendez-vous introuvable avec l'ID : " + id));

        // Mettre à jour l'état si fourni
        if (updateData.containsKey("etat")) {
            rdv.setEtat(updateData.get("etat"));
        }

        return rdv;
    }

}
