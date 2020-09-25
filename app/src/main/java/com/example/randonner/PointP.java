package com.example.randonner;

import com.mapbox.mapboxsdk.geometry.LatLng;

public class PointP {

    // les Attributs
    private LatLng point;
    private String name;

    public PointP(LatLng point ,String name){
        this.name=name;
        this.point=point;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPoint(LatLng point) {
        this.point = point;
    }

    public LatLng getPoint() {
        return point;
    }
}
