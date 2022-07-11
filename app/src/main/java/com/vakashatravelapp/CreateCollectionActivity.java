package com.vakashatravelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vakashatravelapp.databinding.ActivityCreateCollectionBinding;
import com.vakashatravelapp.databinding.ActivityDashboardBinding;

import java.util.ArrayList;


// this activity will allow a user to create a new collection
public class CreateCollectionActivity extends DrawerBaseActivity {

    // navbar variable declaration
    ActivityCreateCollectionBinding activityCreateCollectionBinding;

    // declaring variables for layout items
    TextView edCollectionName;
    TextView edCollectionDescription;
    TextView edCollectionGoal;
    Button btnSaveCollection;
    TripCollection tripCollection;
    private ArrayList<Trip> trips; // array to store trips

    // Database references
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://vakashatravelapptest-default-rtdb.firebaseio.com/");
    DatabaseReference vakashaRef = database.getReference("collections");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // navbar initialization
        activityCreateCollectionBinding = ActivityCreateCollectionBinding.inflate(getLayoutInflater());
        setContentView(activityCreateCollectionBinding.getRoot());
        allocatedActivityTitle("Create a Collection");

        // initializing variables for layout items
        edCollectionName = findViewById(R.id.editCollectionName);
        edCollectionDescription = findViewById(R.id.editTextCollectionDescription);
        edCollectionGoal = findViewById(R.id.editCollectionGoal);
        btnSaveCollection = findViewById(R.id.btnSaveCollection);
        tripCollection = new TripCollection();
        trips = new ArrayList<>();


        // Method call when user clicks on Save Collection button
        btnSaveCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // setting trip properties
                tripCollection.setCollectionName(edCollectionName.getText().toString());
                tripCollection.setCollectionDescription(edCollectionDescription.getText().toString());
                tripCollection.setCollectionGoal(Integer.parseInt(edCollectionGoal.getText().toString()));
                tripCollection.setTrips(trips);

                // code to ensure user enters all fields before trip is saved to database
                if (tripCollection.getCollectionName().equalsIgnoreCase("")){
                    Toast.makeText(CreateCollectionActivity.this, "please enter all fields", Toast.LENGTH_SHORT).show();
                }
                else{

                    // this code saves trip to cloud and then opens the ViewCollectionsActivity
                    vakashaRef.push().setValue(tripCollection);
                    Toast.makeText(CreateCollectionActivity.this, "Collection Saved to Cloud :)", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(CreateCollectionActivity.this, ViewCollectionsActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}