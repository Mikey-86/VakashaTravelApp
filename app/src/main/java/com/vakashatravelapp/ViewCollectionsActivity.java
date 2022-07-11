package com.vakashatravelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vakashatravelapp.databinding.ActivityDashboardBinding;
import com.vakashatravelapp.databinding.ActivityViewCollectionsBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// this activity will allow the user to view a list of all their different types of collections

public class ViewCollectionsActivity extends DrawerBaseActivity {

    ActivityViewCollectionsBinding activityViewCollectionsBinding;

    // declaring variables
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://vakashatravelapptest-default-rtdb.firebaseio.com/");
    DatabaseReference vakashaRef = database.getReference("collections"); // database of collections

    Button btnCreateNewCollection;

    // objects to store trips retrieved from databases
    private ListView lstTripCollections;
    private List<String> tripCollectionList;
    private List<TripCollection> tripCollections;
    private ArrayAdapter<String> tripCollectionAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityViewCollectionsBinding = activityViewCollectionsBinding.inflate(getLayoutInflater());

        setContentView(activityViewCollectionsBinding.getRoot());
           allocatedActivityTitle("View collections");
        btnCreateNewCollection = findViewById(R.id.btnAddCollection);
        tripCollectionList = new ArrayList<>();
        tripCollections = new ArrayList<>();
        lstTripCollections = findViewById(R.id.lstTripCollections);

        vakashaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tripCollectionList.clear();
                for(DataSnapshot pullTripCollection: snapshot.getChildren()){
                    TripCollection tripCollection = new TripCollection();
                    tripCollection.setCollectionName(pullTripCollection.child("collectionName").getValue().toString());
                    tripCollection.setCollectionGoal(Integer.parseInt(pullTripCollection.child("collectionGoal").getValue().toString()));
                    tripCollection.setCollectionDescription(pullTripCollection.child("collectionDescription").getValue().toString());
                    tripCollection.setDatabaseKey(pullTripCollection.getKey());
                    tripCollectionList.add(tripCollection.getCollectionName());

                    // store trip object to trip list
                    tripCollections.add(tripCollection);
                }
                tripCollectionAdapter = new ArrayAdapter<String>(ViewCollectionsActivity.this, android.R.layout.simple_list_item_1, tripCollectionList);
                lstTripCollections.setAdapter(tripCollectionAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewCollectionsActivity.this, "Error reading from Collection Database", Toast.LENGTH_SHORT).show();
            }
        });

        // what happens when you click on item from past collection list??
        lstTripCollections.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // get TripCollection object from trip list
                TripCollection collectionTrip1 = tripCollections.get(i);
                //collectionTrip1.

                // create new intent and send pulled trip object to to trip details activity
                Intent intent = new Intent(ViewCollectionsActivity.this, ViewSingleCollection.class);
                intent.putExtra("tripCollectionName", collectionTrip1.getCollectionName());
                intent.putExtra("tripCollectionGoal", collectionTrip1.getCollectionGoal());
                intent.putExtra("tripCollectionDetails", collectionTrip1.getCollectionDescription());
                intent.putExtra("databaseKey", collectionTrip1.getDatabaseKey());
                intent.putExtra("trips", collectionTrip1.getTrips());
                startActivity(intent);
            }
        });










        // what happens when a user wants to create a new collection
        // opens up the create collection activity
        btnCreateNewCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ViewCollectionsActivity.this, CreateCollectionActivity.class);
                startActivity(i);
            }
        });
    }
}