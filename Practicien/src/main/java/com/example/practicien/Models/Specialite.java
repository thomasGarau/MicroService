package com.example.practicien.Models;

public class Specialite {

    private String nom;
    private final int id;
    private static int count = 0;


    public Specialite() {
        count++;
        this.id = count;
    }

    public Specialite(String nom) {
        this.nom = nom;
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

}
