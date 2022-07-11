package com.vakashatravelapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vakashatravelapp.databinding.ActivityNewTripSnapsBinding;

// Activity for adding taking and adding picture
public class NewTripSnapsActivity extends DrawerBaseActivity {

    // declaring variables for layout items
    private FloatingActionButton fab;
    private ImageView imgCameraImage;
    private static final int REQUEST_IMAGE_CAPTURE = 0;
    private static final int REQUEST_IMAGE_CAPTURE_PERMISSION = 100;
    ActivityNewTripSnapsBinding activityNewTripSnapsBinding;


    @Override
    protected void onCreate (Bundle savedInstancesState){
        super.onCreate(savedInstancesState);
        setContentView(R.layout.activity_new_trip_snaps);

        activityNewTripSnapsBinding = ActivityNewTripSnapsBinding.inflate(getLayoutInflater());
        setContentView(activityNewTripSnapsBinding.getRoot());
        allocatedActivityTitle("Add Photo");

        fab = findViewById(R.id.photoFab);
        imgCameraImage = findViewById(R.id.imgPlaceHolder);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if we have camera permission
                if (ActivityCompat.checkSelfPermission(NewTripSnapsActivity.this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    final String[] permissions = {Manifest.permission.CAMERA};
                    // Request permission - this is asynchronous
                    ActivityCompat.requestPermissions(NewTripSnapsActivity.this,
                            permissions, REQUEST_IMAGE_CAPTURE_PERMISSION);
                    System.out.println("not taking photo");
                } else {
                    // We have permission, so take the photo
                    System.out.println("Taking photo");
                    takePhoto();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_IMAGE_CAPTURE_PERMISSION &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                        PackageManager.PERMISSION_GRANTED) {
            // We have permission, so take the photo
            takePhoto();
        }
    }

    private void takePhoto() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, REQUEST_IMAGE_CAPTURE);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check if we are receiving the result from the right request.
        // Also check whether the data is null, meaning the user may have cancelled.
        if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgCameraImage.setImageBitmap(bitmap);
        }
    }
}