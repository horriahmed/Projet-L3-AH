package com.example.randonner;

import android.support.annotation.Nullable;

import java.util.List;

public class User {
    private String uid;
    private String firstName;
    private String lastName;
    private String mail;
    private String tel;
    private List<Parcours> parcours;
    private List<PointInteret> pointInterets;
    @Nullable private String urlPicture;

    public User(String uid, String firstName, String lastName, String mail, String tel, List<Parcours> parcours, List<PointInteret> pointInterets, @Nullable String urlPicture) {
        this.uid=uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.tel = tel;
        this.parcours = parcours;
        this.pointInterets = pointInterets;
        this.urlPicture = urlPicture;
    }

    public User(String uid,String firstName, String lastName, String mail, String tel, @Nullable String urlPicture) {
        this.uid=uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.tel = tel;
        this.urlPicture = urlPicture;
    }

    public String getUid() {
        return uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMail() {
        return mail;
    }

    public String getTel() {
        return tel;
    }

    public List<Parcours> getParcours() {
        return parcours;
    }

    public List<PointInteret> getPointInterets() {
        return pointInterets;
    }

    @Nullable
    public String getUrlPicture() {
        return urlPicture;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setParcours(List<Parcours> parcours) {
        this.parcours = parcours;
    }

    public void setPointInterets(List<PointInteret> pointInterets) {
        this.pointInterets = pointInterets;
    }

    public void setUrlPicture(@Nullable String urlPicture) {
        this.urlPicture = urlPicture;
    }
}
