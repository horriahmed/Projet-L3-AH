package com.example.randonner;

public class PointInteret {
    private int id_utilisateur;
    private String nom,description;
    private double latitude,longitude;

    public PointInteret(int id_utilisateur, String nom, String description, double latitude, double longitude) {
        this.id_utilisateur = id_utilisateur;
        this.nom = nom;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
