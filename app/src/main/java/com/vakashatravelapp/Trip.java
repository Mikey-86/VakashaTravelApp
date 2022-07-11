package com.vakashatravelapp;


import java.util.ArrayList;

// the trip class
public class Trip {

    // trip properties
    private String tripName;
    private String tripCountry;
    private String tripDescription;
    private String tripDate;
    private String tripGoals;




    // constructors
    public Trip(String tripName, String tripCountry, String tripDescription, String tripDate) {
        this.tripName = tripName;
        this.tripCountry = tripCountry;
        this.tripDescription = tripDescription;
        this.tripDate = tripDate;
    }


    public Trip(String tripName, String tripCountry, String tripDate) {
        this.tripName = tripName;
        this.tripCountry = tripCountry;
        this.tripDate = tripDate;
    }


    public Trip() {
    }

    public Trip(String tripName, String tripCountry, String tripDescription, String tripDate, String tripKey) {
        this.tripName = tripName;
        this.tripCountry = tripCountry;
        this.tripDescription = tripDescription;
        this.tripDate = tripDate;
        this.tripKey = tripKey;
    }


    // getters and setters
    public String getTripGoals() {
        return tripGoals;
    }

    public void setTripGoals(String tripGoals) {
        this.tripGoals = tripGoals;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getTripCountry() {
        return tripCountry;
    }

    public void setTripCountry(String tripCountry) {
        this.tripCountry = tripCountry;
    }

    public String getTripDescription() {
        return tripDescription;
    }

    public void setTripDescription(String tripDescription) {
        this.tripDescription = tripDescription;
    }

    public String getTripDate() {
        return tripDate;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }

    public String getTripKey() {
        return tripKey;
    }

    public void setTripKey(String tripKey) {
        this.tripKey = tripKey;
    }

    private String tripKey;


    // method to return trip details
    public String tripSummary(){
        return
                "Trip Name: " + tripName + "\n"
                + "Trip Date: " + tripDate;
    }

    public String bucketTripSummary(){
        return
                "Trip Name: " + tripName + "\n"
                + "Trip Date: " + tripDate;
    }

    @Override
    public String toString() {
        return
                "Trip Name: '" + tripName + '\'' +
                ", Trip Country: '" + tripCountry + '\'' +
                ", Trip Description: '" + tripDescription + '\'' +
                ", Trip Date: '" + tripDate + '\'';
    }


}
