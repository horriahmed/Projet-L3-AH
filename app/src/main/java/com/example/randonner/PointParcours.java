package com.example.randonner;

import com.mapbox.mapboxsdk.geometry.LatLng;

public class PointParcours {
    private int id_utilisateur,id_parcours;
    private double latitude,longitude;
    private int position;

    public PointParcours(int id_utilisateur, int id_parcours, double latitude, double longitude, int position) {
        this.id_utilisateur = id_utilisateur;
        this.id_parcours = id_parcours;
        this.latitude = latitude;
        this.longitude = longitude;
        this.position = position;
    }

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public int getId_parcours() {
        return id_parcours;
    }

    public void setId_parcours(int id_parcours) {
        this.id_parcours = id_parcours;
    }

    public LatLng getPointCoordinate(){
        LatLng latLng=new LatLng(latitude,longitude);
        return latLng;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    @Override
    public String toString() {
        return "latitude : "+latitude+" | longitude : "+longitude+" | position : "+position;
    }

}
