package com.vakashatravelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;

// this is where the functionality  of the navbar happens

//
public class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

//Calling the drawer layout
    DrawerLayout drawerLayout;

    //where the navbar is displayed
    @Override
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout)  getLayoutInflater().inflate(R.layout.activity_drawer_base, null);
        FrameLayout container = drawerLayout.findViewById(R.id.activitycontainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this,drawerLayout,toolbar, R.string.menu_drawer_open,  R.string.menu_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    //menu items for the navbar
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);

        switch ( item.getItemId()){
            case R.id.nav_customers:
                startActivity(new Intent(this,HomePageActivity.class));
        }
        switch ( item.getItemId()){
            case R.id.nav_orders:
                startActivity(new Intent(this,NewTripActivity.class));
        }

        switch ( item.getItemId()){
            case R.id.nav_riders:
                startActivity(new Intent(this,TripHistoryActivity.class));
        }

        switch ( item.getItemId()){
            case R.id.nav_sales:
                startActivity(new Intent(this,ViewCollectionsActivity.class));
        }





        return false;

    }
//Method for the title of the navbar
    protected void allocatedActivityTitle(String titleString){
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(titleString);
        }

    }
}
