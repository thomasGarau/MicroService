package com.example.patient.Models;

public class Patient {

    private String nom;
    private String prenom;
    private int age;
    private final int id;
    private static int count = 0;


    public Patient(String nom, String prenom, int age) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
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

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
