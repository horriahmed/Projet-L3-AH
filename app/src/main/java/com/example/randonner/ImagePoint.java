package com.example.randonner;

public class ImagePoint {

    private String titre;
    private Integer image;

    public ImagePoint(String titre,Integer image){
        this.titre=titre;
        this.image=image;
    }

    public ImagePoint(Integer image){
        this.image=image;
    }


    public void setImage(Integer image){
        this.image=image;
    }
    public Integer getImage(){
        return image;
    }
    public void setTitre(String titre){
        this.titre=titre;
    }
    public String getTitre(){
        return this.titre;
    }
}
