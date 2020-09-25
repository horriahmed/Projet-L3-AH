package com.example.randonner;

public class Point {
    private Double latitude,longitude;

    public Point(double latitude, double longitude){
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public void setLatitude(Double latitude){
        this.latitude=latitude;
    }
    public Double getLatitude(){
        return latitude;
    }
    public void setLongitude(Double longitude){
        this.longitude=longitude;
    }
    public Double getLongitude(){
        return longitude;
    }



}