package com.example.practicien.Models;

import com.example.practicien.Models.Specialite;


public class Practicien {

    private String nom;
    private String prenom;
    private Specialite specialite;
    private final int id;
    private static int count = 0;


    public Practicien() {
        count++;
        this.id = count;
    }

    public Practicien(String nom, String prenom, Specialite specialite) {
        this.nom = nom;
        this.prenom = prenom;
        this.specialite = specialite;
        count++;
        this.id = count;
    }

    public int getId() {
        return this.id;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Specialite getSpecialite() {
        return this.specialite;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }
}
