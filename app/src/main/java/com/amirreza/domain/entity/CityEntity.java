package com.amirreza.domain.entity;


public class CityEntity {
    private double lat;
    private double lon;
    private String city;
    private String country;

    public CityEntity(double lat, double lon, String city, String country) {
        this.lat = lat;
        this.lon = lon;
        this.city = city;
        this.country = country;
    }
    public CityEntity(String lat, String lon) {
        this.lat = Double.parseDouble(lat);
        this.lon = Double.parseDouble(lon);
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}

