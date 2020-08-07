package com.example.t23_pm2020;

public class location {
    public int getLid() {
        return lid;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    private int lid;
    private String street;
    private String neighborhood;
    private String name;
    private double lat;
    private double lon;
    private String type;
    private String reportType = "";
    private String reportsNum = "";

    public location(int lid, String street, String neighborhood, String name, double lat, double lon, String type) {
        this.lid = lid;
        this.street = street;
        this.neighborhood = neighborhood;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.type = type;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportsNum() {
        return reportsNum;
    }

    public void setReportsNum(String reportsNum) {
        this.reportsNum = reportsNum;
    }
}
