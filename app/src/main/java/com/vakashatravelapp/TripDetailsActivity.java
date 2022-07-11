package com.vakashatravelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vakashatravelapp.databinding.ActivityDashboardBinding;
import com.vakashatravelapp.databinding.ActivityTripDetailsBinding;

import org.w3c.dom.Text;


// this activity pulls the trip from the database and show the details
public class TripDetailsActivity extends DrawerBaseActivity {

    // navbar variable declaration
    ActivityTripDetailsBinding activityTripDetailsBinding;

    // declaring variables for layout items
    private TextView txtViewTripName;
    private TextView txtViewCountryName;
    private TextView txtViewTripDate;
    private TextView txtViewTripDescription;
    private String tripName, countryName, tripDate, tripDescription, tripKey;
    private Button btnViewAllTrips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // navbar initialization
        activityTripDetailsBinding = activityTripDetailsBinding.inflate(getLayoutInflater());
        setContentView(activityTripDetailsBinding.getRoot());
        allocatedActivityTitle("Trip details");

        // initializing variables for layout items
        btnViewAllTrips = findViewById(R.id.btnViewTripHistory);
        tripKey = getIntent().getStringExtra("tripKey");


        // getting trip name details from intent
        txtViewTripName = findViewById(R.id.txtViewTripName);
        tripName = getIntent().getStringExtra("tripName");
        txtViewTripName.setText(tripName);

        // getting trip country name from intent
        txtViewCountryName = findViewById(R.id.txtViewCountryName);
        countryName = getIntent().getStringExtra("tripCountryName");
        txtViewCountryName.setText(countryName);

        // getting trip date from intent
        txtViewTripDate = findViewById(R.id.txtViewTripDate);
        tripDate = getIntent().getStringExtra("tripDate");
        txtViewTripDate.setText(tripDate);

        // getting trip description from intent
        txtViewTripDescription = findViewById(R.id.txtViewTripDetails);
        tripDescription = getIntent().getStringExtra("tripDescription");
        txtViewTripDescription.setText(tripDescription);

        btnViewAllTrips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TripDetailsActivity.this, TripHistoryActivity.class);
                startActivity(i);
            }
        });
    }
}