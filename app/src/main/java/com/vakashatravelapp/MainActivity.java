package com.vakashatravelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// this activity represents the login page that will be shown to the user when they first start the app

public class MainActivity extends AppCompatActivity {

    private EditText edCustomerName; // declare variable that will be assigned to user login
    private EditText edPassword; // declare variable that will be assigned to user password
    private Button loginButton; // declare variable that will be assigned to login button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // this activity will be called when app is started
        String userName = "mesh"; // variable for userName
        String password = "mesh21"; // variable for userPassword

        loginButton = findViewById(R.id.btnLogin); // initiating variable for Login Button
        edCustomerName = findViewById(R.id.edCustomerName); // initiating variable for userName
        edPassword = findViewById(R.id.edPassword); // initiating variable for user password

        // what happens if login button is clicked
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if data from edCustomerName and edPassword is correct then send a toast and show HomePageActivity
                if (userName.equalsIgnoreCase(edCustomerName.getText().toString()) == true && password.equalsIgnoreCase(edPassword.getText().toString()) == true) {
                    Toast.makeText(MainActivity.this, "Correct bruv :)", Toast.LENGTH_SHORT).show(); // toast
                    Intent i = new Intent(MainActivity.this, HomePageActivity.class);
                    startActivity(i); // move to HomePageActivity
                }
                // else show toast saying incorrect login details
                else{
                    Toast.makeText(MainActivity.this, "incorrect bruv :(", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}