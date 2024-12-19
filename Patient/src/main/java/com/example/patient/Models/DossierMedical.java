package com.example.patient.Models;

public class DossierMedical {

    private static int count = 0;
    private final int id;
    private int idPatient;
    private String diagnostic;
    private String traitement;

    public DossierMedical() {
        count++;
        this.id = count;
    }

    public DossierMedical(int idPatient, String diagnostic, String traitement) {
        count++;
        this.id = count;
        this.idPatient = idPatient;
        this.diagnostic = diagnostic;
        this.traitement = traitement;
    }

    public int getId() {
        return id;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public String getTraitement() {
        return traitement;
    }

    public void setTraitement(String traitement) {
        this.traitement = traitement;
    }
}
