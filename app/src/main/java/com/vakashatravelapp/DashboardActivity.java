package com.vakashatravelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vakashatravelapp.databinding.ActivityDashboardBinding;

import java.util.ArrayList;
import java.util.List;

//this file was just to test if the navbar works lol
public class DashboardActivity extends DrawerBaseActivity  {

    ActivityDashboardBinding activityDashboardBinding;
    ArrayList barArrayList;
    private List<Trip> trips;
    private int collectionGoals, numberOfTrips;



    private List<TripCollection> tripCollections;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://vakashatravelapptest-default-rtdb.firebaseio.com/");
    DatabaseReference vakashaRef = database.getReference("collections"); // database of collections


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDashboardBinding = ActivityDashboardBinding.inflate(getLayoutInflater());

        setContentView(R.layout.graph);
        trips = new ArrayList<>();
        // below line is used to get the instance
        // of our Firebase database.
        database = FirebaseDatabase.getInstance();

        // below line is used to get
        // reference for our database.
        vakashaRef = database.getReference("Data");

        // initializing our object class variable.


        // calling method
        // for getting data.




    BarChart barChart = findViewById(R.id.bar_chart);
        getData();
        BarDataSet barDataSet = new BarDataSet(barArrayList, "vakasha trips");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(17f);
        barChart.getDescription().setEnabled(true);

    }

    private void getData(){
//for loop to display the number of trips
        barArrayList = new ArrayList();
        for(int i = 0; i < numberOfTrips; i++) {

        }
        collectionGoals = getIntent().getIntExtra("tripCollectionGoal", 0);

            barArrayList.add(new BarEntry(2f,10));
        barArrayList.add(new BarEntry(3f,20));
        barArrayList.add(new BarEntry(4f,30));
        barArrayList.add(new BarEntry(5f,20));
        barArrayList.add(new BarEntry(6f,50));



        vakashaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // this method is call to get the realtime
                // updates in the data.





                // this method is called when the data is
                // changed in our Firebase console.
                // below line is for getting the data from
                // snapshot of our database.
                String value = snapshot.getValue(String.class);

                // after getting the value we are setting
                // our value to our text view in below line.

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(DashboardActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });




    }

}