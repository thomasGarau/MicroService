package com.example.rdv.Requests;

public class CreateRdvRequest {
    private String date;
    private String heure;
    private int patientId;
    private int practicienId;
    private String motif;
    private String etat;

    public CreateRdvRequest() {
    }

    public CreateRdvRequest(String date, String heure, int patientId, int practicienId, String motif, String etat) {
        this.date = date;
        this.heure = heure;
        this.patientId = patientId;
        this.practicienId = practicienId;
        this.motif = motif;
        this.etat = etat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getPracticienId() {
        return practicienId;
    }

    public void setPracticienId(int practicienId) {
        this.practicienId = practicienId;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}
