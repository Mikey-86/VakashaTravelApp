package com.vakashatravelapp;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.vakashatravelapp.databinding.ActivityViewSingleCollectionBinding;

import java.util.ArrayList;
import java.util.List;

public class ViewSingleCollection extends DrawerBaseActivity {

    ActivityViewSingleCollectionBinding activityViewSingleCollectionsBinding;

    private TextView txtViewCollectionName;
    private TextView txtViewCollectionGoals;
    private TextView txtViewCollectionDetails;
    private TextView textViewNumberOfTrips;
    private String collectionName, collectionDetails, collectionDatabaseKey;
    private int collectionGoals, numberOfTrips;

    private Button btnAddTripToCollection;
    private ListView lstViewTrips;
    private ProgressBar progressBar;
    private ListView lstTripHistory;
    private List<String> tripList;
    private List<Trip> trips;
    private ArrayAdapter<String> tripAdapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://vakashatravelapptest-default-rtdb.firebaseio.com/");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_collection);

        activityViewSingleCollectionsBinding = activityViewSingleCollectionsBinding.inflate(getLayoutInflater());


        setContentView(activityViewSingleCollectionsBinding.getRoot());
        allocatedActivityTitle("Collection Details");
        progressBar = findViewById(R.id.progressBar);

        textViewNumberOfTrips = findViewById(R.id.txtViewNumberOfTrips);

        // initiate trip list names
        tripList = new ArrayList<>();

        trips = new ArrayList<>();

        // initiate number of trips to 0
        //numberOfTrips = 0;

        lstViewTrips = findViewById(R.id.lstViewTrips);

        // get database connection key from intent sent by previous activity
        collectionDatabaseKey = getIntent().getStringExtra("databaseKey");
        DatabaseReference collectionRef = database.getReference("collections").child(collectionDatabaseKey); // create database reference


        // add trips to list
        collectionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                numberOfTrips = 0;


                if (snapshot.child("trips").exists()){
                    tripList.clear();
                    trips.clear();


                    for (DataSnapshot child: snapshot.child("trips").getChildren()) {
                        int i = 0;
                        String key = child.getKey();
                        String tripName = child.child("tripName").getValue().toString();
                        String tripDescription = child.child("tripDescription").getValue().toString();
                        String tripDate = child.child("tripDate").getValue().toString();
                        String tripCountry = child.child("tripCountry").getValue().toString();


                        Trip trip = new Trip(tripName, tripCountry, tripDescription, tripDate, key);
                        trips.add(trip);



                        //String value = child.getValue().toString();
                        tripList.add(tripName);
                        numberOfTrips ++;
                        i++;
                        progressBar.setProgress(numberOfTrips);
                        progressBar.setMax(collectionGoals);


                    }
                    tripAdapter = new ArrayAdapter<String>(ViewSingleCollection.this, android.R.layout.simple_list_item_1, tripList);
                    lstViewTrips.setAdapter(tripAdapter);
                     /// set text view to display trips in collection

                }
                textViewNumberOfTrips.setText(Integer.toString(numberOfTrips));


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewSingleCollection.this, "Error retrieving trips from Database", Toast.LENGTH_SHORT).show();
            }
        });

        lstViewTrips.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Trip trip = trips.get(i);
                Query tripRef = database.getReference("trips").orderByKey().equalTo(trip.getTripKey());
                //DatabaseReference tripRef = database.getReference("trips").child(trip.getTripKey());


                tripRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot post : snapshot.getChildren() ){

                            Trip trip = post.getValue(Trip.class);
                            Intent intent = new Intent(ViewSingleCollection.this, TripDetailsActivity.class);
                            intent.putExtra("tripName", trip.getTripName());
                            intent.putExtra("tripCountryName", trip.getTripCountry());
                            intent.putExtra("tripDate", trip.getTripDate());
                            intent.putExtra("tripDescription", trip.getTripDescription());
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ViewSingleCollection.this, "Error retrieving trip", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });






        btnAddTripToCollection = findViewById(R.id.btnAddTripToCollection);

        txtViewCollectionName = findViewById(R.id.txtViewCollectionName);
        collectionName = getIntent().getStringExtra("tripCollectionName");
        txtViewCollectionName.setText(collectionName);


        txtViewCollectionGoals = findViewById(R.id.txtViewCollectionGoals);
        collectionGoals = getIntent().getIntExtra("tripCollectionGoal", 0);
        txtViewCollectionGoals.setText(Integer.toString(collectionGoals));

        txtViewCollectionDetails = findViewById(R.id.txtViewCollectionDetails);
        collectionDetails = getIntent().getStringExtra("tripCollectionDetails");
        txtViewCollectionDetails.setText(collectionDetails);





        // add exisiting trip to a collection
        btnAddTripToCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewSingleCollection.this, AddTripToCollection.class);
                intent.putExtra("databaseKey", collectionDatabaseKey);
                startActivity(intent);
            }
        });

    }
}