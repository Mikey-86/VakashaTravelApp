package com.vakashatravelapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vakashatravelapp.databinding.ActivityAddTripToCollectionBinding;
import com.vakashatravelapp.databinding.ActivityDashboardBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


// this activity handles adding a trip to a collection
public class AddTripToCollection extends DrawerBaseActivity {

    // navbar variable declaration
    ActivityAddTripToCollectionBinding activityAddTripToCollectionBinding;

    // Database references
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://vakashatravelapptest-default-rtdb.firebaseio.com/");
    DatabaseReference tripsRef = database.getReference("trips"); // database of past trips


    private TextView txtViewCollectionName;

    // objects to store trips retrieved from databases
    private ListView lstTripHistory;
    private List<String> tripList;
    private List<Trip> trips;
    private ArrayAdapter<String> tripAdapter;
    private String dataBaseKey;
    private TripCollection tripCollection;
    private int tripCollectionSize; /// keep track of how many items in collection
    private Button btnSaveNewTrip;

    // a HashMap object that will hold all the trips for a collection
    private HashMap <String, Trip> tripCollectionHash = tripCollectionHash = new HashMap<String, Trip>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        tripCollectionSize = tripCollectionHash.size(); // Hashmap size

        super.onCreate(savedInstanceState);
      activityAddTripToCollectionBinding = ActivityAddTripToCollectionBinding.inflate(getLayoutInflater());

        // navbar initialization
        setContentView(activityAddTripToCollectionBinding.getRoot());
        allocatedActivityTitle("Add trip to collection");

        // getting the data base key of the TripCollection Object from previous ViewCollectionsActivity
        dataBaseKey = getIntent().getStringExtra("databaseKey");
        DatabaseReference collectionRef = database.getReference("collections").child(dataBaseKey); // initializing new database ref

        // initializing variables
        btnSaveNewTrip = findViewById(R.id.btnSaveNewTrip); // save button from layout
        tripList = new ArrayList<>();
        trips = new ArrayList<>();
        lstTripHistory = findViewById(R.id.lstTripHistory);
        txtViewCollectionName = findViewById(R.id.txtViewCollectionName); // setting textview

        // method to retrieve collection from database and update it if any changes
        collectionRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Trip trip; // declaring new trip object
                tripCollection = new TripCollection(); // declaring new database object
                tripCollection.setCollectionName(snapshot.child("collectionName").getValue().toString()); // setting TripCollection object name to value from database
                txtViewCollectionName.setText(tripCollection.getCollectionName()); // setting textvalue of layout to the Collection Name

                // if the collection already has trips in it
                if (snapshot.child("trips").exists()){

                    // then for each trip get details and add it to HashMap
                    for (DataSnapshot child: snapshot.child("trips").getChildren()) {
                        String key = child.getKey();
                        String tripCountry = child.child("tripCountry").getValue().toString();
                        String tripDate = child.child("tripDate").getValue().toString();
                        String tripDescription = child.child("tripDescription").getValue().toString();
                        String tripName = child.child("tripName").getValue().toString();
                        trip = new Trip(tripName, tripCountry, tripDescription, tripDate); // create trip
                        tripCollectionHash.put(key, trip); // adding the trip to the HashMap
                    }
                }
            }
            // If there is a problem connecting to the database then notify user using toast
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddTripToCollection.this, "Mistake in Retrieving object", Toast.LENGTH_SHORT).show();
            }
        });

        // method to load all saved trips to list so a user can add them to a collection
        tripsRef.addValueEventListener(new ValueEventListener() {
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
                tripAdapter = new ArrayAdapter<String>(AddTripToCollection.this, android.R.layout.simple_list_item_1, tripList);
                lstTripHistory.setAdapter(tripAdapter); // setting the list view
            }
            // If there is a problem connecting to the database then notify user using toast
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddTripToCollection.this, "Error reading from Trip History Database", Toast.LENGTH_SHORT).show();
            }
        });

        // method called when you click on trip from trip list
        lstTripHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Trip trip1 = trips.get(i); // get relevant trip from trips ArrayList

                // add it to hashmap
                // if trip already exists in this collection then notify user using toast
                if(tripCollectionHash.containsKey(trip1.getTripKey())){
                    Toast.makeText(AddTripToCollection.this, "This trip already exists in this database", Toast.LENGTH_SHORT).show();
                }
                // else add it to hashmap and update database.collections using updated hashmap
                else{
                    tripCollectionHash.put(trip1.getTripKey(), trip1);
                    collectionRef.child("trips").setValue(tripCollectionHash);
                    Toast.makeText(AddTripToCollection.this, "Added Trip", Toast.LENGTH_SHORT).show();

                }

            }
        });

        // button functionality to add new trip
        btnSaveNewTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddTripToCollection.this, NewTripActivity.class);
                startActivity(i);
            }
        });
    }
}