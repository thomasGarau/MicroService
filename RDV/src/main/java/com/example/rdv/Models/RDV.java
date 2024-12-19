package com.example.rdv.Models;

public class RDV {

    private static int count = 0;
    private int id;
    private String date;
    private String heure;
    private Patient patient;
    private Practicien practicien;
    private String motif;
    private String etat;

    public RDV() {
        count++;
        this.id = count;
    }

    public RDV(String date, String heure, Patient patient, Practicien practicien, String motif, String etat) {
        count++;
        this.id = count;
        this.date = date;
        this.heure = heure;
        this.patient = patient;
        this.practicien = practicien;
        this.motif = motif;
        this.etat = etat;
    }

    public int getId() {
        return id;
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

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Practicien getPracticien() {
        return practicien;
    }

    public void setPracticien(Practicien practicien) {
        this.practicien = practicien;
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
