package com.vakashatravelapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


// this intent helper class allows the application to share a Objects and data between Activities
public class IntentHelper {

    // share trip object
    public static void openIntent(Context context, String trip, Class passTo) {
        Intent i = new Intent(context, passTo);
        i.putExtra("trip", trip);
        context.startActivity(i);
    }

    // share data
    public static void shareIntent(Context context, String trip) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, trip);
        sendIntent.setType("plain/text");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        context.startActivity(shareIntent);
    }

    // share bundle
    public static void shareIntent(Context context, String tripName, String countryName, String tripDescription){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);

        //create a bundle
        Bundle shareOrderDetails = new Bundle();
        shareOrderDetails.putString("tripName", tripName);
        shareOrderDetails.putString("countryName", countryName);
        shareOrderDetails.putString("tripDescription", tripDescription);

        sendIntent.setType("plain/text");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        context.startActivity(shareIntent);
    }

}