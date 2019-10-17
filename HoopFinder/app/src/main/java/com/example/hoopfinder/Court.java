package com.example.hoopfinder;

public class Court {
    private int id;
    private String name;
    private double longitude;
    private double latitude;

    public Court(int id, String name, double latitude, double longitude){
        this.id=id;
        this.name=name;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public double getLongitude(){
        return this.longitude;
    }

    public double getLatitude(){
        return this.latitude;
    }

    public String getName(){
        return this.name;
    }

}
