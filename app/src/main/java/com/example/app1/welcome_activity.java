package com.example.app1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class welcome_activity extends AppCompatActivity {
    private static final int SPLASH_SCREEN_TIMEOUT = 2000; // 2 seconds
    private static final int IMAGE_ROTATION_INTERVAL = 1000;
    private ImageView splashImageView;
    private Handler handler = new Handler();
    private List<Uri> splashImages = new ArrayList<>();
    private int currentImageIndex = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        SharedPreferences sp1= this.getSharedPreferences("user",MODE_PRIVATE);
        // Reference to the ImageView for splash image
        splashImageView = findViewById(R.id.splash_image);
        SharedPreferences sp = getSharedPreferences("user_prefs", MODE_PRIVATE);

        // Load splash images URIs from SharedPreferences
        Set<String> imageUris = sp.getStringSet("splash_images", null);
//        if (imageUris != null && !imageUris.isEmpty()) {
//            for (String uriString : imageUris) {
//                splashImages.add(Uri.parse(uriString));
//            }
//            // Start rotating images if multiple are uploaded
//            rotateSplashImages();
//        }



        new Handler().postDelayed(() -> {

            if(!(sp1.getString("user","").isEmpty())){
              Intent  intent = new Intent(welcome_activity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }else {
                Intent intent = new Intent(welcome_activity.this, login.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN_TIMEOUT);
    }

    private void rotateSplashImages() {
        // Rotate the splash images every IMAGE_ROTATION_INTERVAL milliseconds
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!splashImages.isEmpty()) {
                    splashImageView.setImageURI(splashImages.get(currentImageIndex));
                    currentImageIndex = (currentImageIndex + 1) % splashImages.size();  // Loop through the images
                }
                // Repeat the rotation after the specified interval
                rotateSplashImages();
            }
        }, IMAGE_ROTATION_INTERVAL);
    }

}