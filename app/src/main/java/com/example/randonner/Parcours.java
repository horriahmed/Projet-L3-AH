package com.example.randonner;

import android.os.Parcel;

import android.os.Parcelable;

import java.io.Serializable;

import java.util.ArrayList;

public class Parcours implements Parcelable, Serializable {

    private int id_parcours,id_utilisateur;

    private String nom,date,description;

    private ArrayList<PointParcours> points;

    public ArrayList<PointParcours> getPoints() {

        return points;

    }

    public void addPoint(PointParcours point) {

        this.points.add(point);

    }

    public void addPoints(ArrayList<PointParcours> points) {

        this.points = points;

    }

    public Parcours(int id_parcours, int id_utilisateur, String nom, String date, String description) {

        this.id_parcours = id_parcours;

        this.id_utilisateur = id_utilisateur;

        this.nom = nom;

        this.date = date;

        this.description = description;

        points=new ArrayList<>();

    }

    protected Parcours(Parcel in) {

        id_parcours = in.readInt();

        id_utilisateur = in.readInt();

        nom = in.readString();

        date = in.readString();

        description = in.readString();

    }

    public static final Creator<Parcours> CREATOR = new Creator<Parcours>() {

        @Override

        public Parcours createFromParcel(Parcel in) {

            return new Parcours(in);

        }

        @Override

        public Parcours[] newArray(int size) {

            return new Parcours[size];

        }

    };

    public int getId_parcours() {

        return id_parcours;

    }

    public void setId_parcours(int id_parcours) {

        this.id_parcours = id_parcours;

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

    public String getDate() {

        return date;

    }

    public void setDate(String date) {

        this.date = date;

    }

    public String getDescription() {

        return description;

    }

    public void setDescription(String description) {

        this.description = description;

    }

    @Override

    public String toString() {

        return nom+"\n"+date;

    }

    @Override

    public int describeContents() {

        return 0;

    }

    @Override

    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id_parcours);

        dest.writeInt(id_utilisateur);

        dest.writeString(nom);

        dest.writeString(date);

        dest.writeString(description);

    }

}