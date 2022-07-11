package com.vakashatravelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vakashatravelapp.databinding.ActivityNewTripBinding;

import java.util.ArrayList;
import java.util.List;


// this activity allows a user to add a new trip
public class NewTripActivity extends DrawerBaseActivity {

    // navbar variable declaration
    ActivityNewTripBinding  activityNewTripBinding;


    // declaring variables for layout items
    private EditText edTripName;
    private EditText edCountryName;
    private EditText edTripDate;
    private EditText edTripDescription;
    private ImageButton btnCapturePhoto;
    private Button btnSaveToCloud;

    private Trip trip = new Trip(); // creating a new trip object


    // connecting to firebase database
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://vakashatravelapptest-default-rtdb.firebaseio.com/");
    DatabaseReference vakashaRef = database.getReference("trips");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // navbar initialization
        activityNewTripBinding = ActivityNewTripBinding.inflate(getLayoutInflater());
        setContentView(activityNewTripBinding.getRoot());
        allocatedActivityTitle("Add new trips");

        // initializing variables for layout items
        edTripName = findViewById(R.id.edTripName);
        edCountryName = findViewById(R.id.edCountryName);
        edTripDate = findViewById(R.id.edTripDate);
        edTripDescription = findViewById(R.id.edTripDescription);
        btnSaveToCloud = findViewById(R.id.btnSaveToCloud);
        btnCapturePhoto = findViewById(R.id.btnAddImages);

        // button that saves trip to cloud when clicked
        btnSaveToCloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // setting trip properties
                trip.setTripName(edTripName.getText().toString());
                trip.setTripCountry(edCountryName.getText().toString());
                trip.setTripDate(edTripDate.getText().toString());
                trip.setTripDescription(edTripDescription.getText().toString());


                // code to ensure user enters all fields before trip is saved to database
                if (trip.getTripName().equalsIgnoreCase("")  || trip.getTripCountry().equalsIgnoreCase("") || trip.getTripDate().equalsIgnoreCase("") | trip.getTripDescription().equalsIgnoreCase("")){
                    Toast.makeText(NewTripActivity.this, "please enter all fields", Toast.LENGTH_SHORT).show();
                }
                else{

                    // this code saves trip to cloud and then opens the TripDetailsActivity
                    vakashaRef.push().setValue(trip);
                    Toast.makeText(NewTripActivity.this, "Trip Saved to Cloud :)", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(NewTripActivity.this, TripDetailsActivity.class);
                    i.putExtra("tripName", trip.getTripName());
                    i.putExtra("tripCountryName", trip.getTripCountry());
                    i.putExtra("tripDate", trip.getTripDate());
                    i.putExtra("tripDescription", trip.getTripDescription());
                    startActivity(i);

                }
            }
        });


        // this method will be called when the user clicks the add add photo button
        // opens up NewTripSnapsActivity
        btnCapturePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NewTripActivity.this, NewTripSnapsActivity.class);
                i.putExtra("tripName", trip.getTripName());
                i.putExtra("tripCountryName", trip.getTripCountry());
                i.putExtra("tripDate", trip.getTripDate());
                i.putExtra("tripDescription", trip.getTripDescription());
                startActivity(i);
            }
        });



    }
}