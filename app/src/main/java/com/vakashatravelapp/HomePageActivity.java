package com.vakashatravelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.vakashatravelapp.databinding.ActivityHomePageBinding;


/* this activity handles all the code for the home page and is the activity that starts after the user
 has successfully logged in */

//method that extends the navbar class(DrawerBaseActivity) for the navabr to be viewed in different pages, ive done this to all the pages
public class HomePageActivity extends DrawerBaseActivity {

    //binding the navbar with different classes
    ActivityHomePageBinding activityCustomerBinding;

    private ImageView img_sb1;
    private TextView btnAddTrip; // declare variable that will be assigned to the "Add Trip" button
    private TextView btnViewHistory; // declare variable that will be assigned to "View Trip" History button
    private TextView btnViewCollections; // declare variable that will be assigned to "View Collection Button"
    private TextView addcollection; // declare variable that will be assigned to "View Collection Button"
private TextView Graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // // navbar initialization
        activityCustomerBinding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(activityCustomerBinding.getRoot());
        allocatedActivityTitle("Home");

        // initializing variables for layout items
        btnAddTrip = findViewById(R.id.btnAddTrip); // initialize variable
        btnViewHistory = findViewById(R.id.btnViewTripHistory); // initialize variable
        btnViewCollections = findViewById(R.id.btnCollection); // initialize variable
        addcollection = findViewById(R.id.AddCollection); // initialize variable
        Graph = findViewById(R.id.graph);

        // code that runs when "Add New Trip" button is clicked
        // opens up NewTripActivity

        Graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePageActivity.this, DashboardActivity.class);
                startActivity(i);
            }
        });


        btnAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePageActivity.this, NewTripActivity.class);
                startActivity(i);
            }
        });

        // code that runs when "Add New Trip" button is clicked
        // opens up CreateCollectionActivity
        addcollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePageActivity.this, CreateCollectionActivity.class);
                startActivity(i);
            }
        });

        // code that runs when "View Trips" button is clicked
        // opens up TripHistoryActivity
        btnViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePageActivity.this, TripHistoryActivity.class);
                startActivity(i);
            }
        });

        // code that runs when "Collections" button is clicked
        // opens up ViewCollectionsActivity
        btnViewCollections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePageActivity.this, ViewCollectionsActivity.class);
                startActivity(i);
            }
        });
    }
}