package com.vakashatravelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vakashatravelapp.databinding.ActivityDashboardBinding;
import com.vakashatravelapp.databinding.ActivityTripHistoryBinding;

import java.util.ArrayList;
import java.util.List;


// this activity allows the user to view their past trips
public class TripHistoryActivity extends DrawerBaseActivity {

    // navbar variable declaration
    ActivityTripHistoryBinding activityTripHistoryBinding;

    // Database references
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://vakashatravelapptest-default-rtdb.firebaseio.com/");
    DatabaseReference vakashaRef = database.getReference("trips"); // database of past trips


    // objects to store trips retrieved from databases
    private ListView lstTripHistory;
    private List<String> tripList;
    private List<Trip> trips;
    private ArrayAdapter<String> tripAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // navbar initialization
        activityTripHistoryBinding = activityTripHistoryBinding.inflate(getLayoutInflater());
        setContentView(activityTripHistoryBinding.getRoot());
        allocatedActivityTitle("View Trips");

        // initializing variables
        tripList = new ArrayList<>();
        trips = new ArrayList<>();
        lstTripHistory = findViewById(R.id.lstTripHistory);

        // method to load all saved trips to list so a user can view them
        vakashaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // pull all trips from database.trips
                for(DataSnapshot pullTrip: snapshot.getChildren()){
                    Trip trip = pullTrip.getValue(Trip.class);
                    trip.setTripKey(pullTrip.getKey());
                    tripList.add(trip.tripSummary());

                    // store trip object to trip list
                    trips.add(trip);
                }
                tripAdapter = new ArrayAdapter<String>(TripHistoryActivity.this, android.R.layout.simple_list_item_1, tripList);
                lstTripHistory.setAdapter(tripAdapter);  // setting the list view
            }
            // If there is a problem connecting to the database then notify user using toast
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TripHistoryActivity.this, "Error reading from Trip History Database", Toast.LENGTH_SHORT).show();
            }
        });

        // mehtod called when user clicks on trip from list
        lstTripHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // get trip object from trip list
                Trip trip1 = trips.get(i);

                // create new intent and send pulled trip object to to TripDetailsActivity
                Intent intent = new Intent(TripHistoryActivity.this, TripDetailsActivity.class);
                intent.putExtra("tripName", trip1.getTripName());
                intent.putExtra("tripCountryName", trip1.getTripCountry());
                intent.putExtra("tripDate", trip1.getTripDate());
                intent.putExtra("tripDescription", trip1.getTripDescription());
                intent.putExtra("tripKey", trip1.getTripKey());
                startActivity(intent);
            }
        });
    }
}