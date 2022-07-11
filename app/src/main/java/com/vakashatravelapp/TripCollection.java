package com.vakashatravelapp;

import java.util.ArrayList;
import java.util.List;


// this class will create a tripCollection object that will capture details about the trip collection and
// store it in a list type "Trip"
public class TripCollection {
    private String collectionName;
    private String collectionDescription;
    private int collectionGoal;
    private String databaseKey;
    public ArrayList<Trip> trips = new ArrayList<>(); // arrayList to store all trips in this collection



    // contstructors
    public TripCollection() {
    }

    public TripCollection(String collectionName, String collectionDescription, int collectionGoal, ArrayList<Trip> trips) {
        this.collectionName = collectionName;
        this.collectionDescription = collectionDescription;
        this.collectionGoal = collectionGoal;
        this.trips = trips;
    }


    // getters and setters
    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getCollectionDescription() {
        return collectionDescription;
    }

    public void setCollectionDescription(String collectionDescription) {
        this.collectionDescription = collectionDescription;
    }

    public int getCollectionGoal() {
        return collectionGoal;
    }

    public void setCollectionGoal(int collectionGoal) {
        this.collectionGoal = collectionGoal;
    }

    public ArrayList<Trip> getTrips() {
        return trips;
    }


    public void setTrips(ArrayList<Trip> trips) {
        this.trips = trips;
    }

    public String getDatabaseKey() {
        return databaseKey;
    }

    public void setDatabaseKey(String databaseKey) {
        this.databaseKey = databaseKey;
    }

}
