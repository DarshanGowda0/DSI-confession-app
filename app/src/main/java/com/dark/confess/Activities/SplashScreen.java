package com.dark.confess.Activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.dark.confess.Utilities.Constants;
import com.dark.confess.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        requestPermission();

    }

    void requestPermission() {
        if (ContextCompat.checkSelfPermission(SplashScreen.this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(SplashScreen.this, new String[]{android.Manifest.permission.READ_PHONE_STATE}, Constants.permissionCode);
        else
            thread.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.permissionCode) {
            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                thread.start();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Please grant permission to use this app", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {

            try {
                Thread.sleep(2000);

                startActivity(new Intent(SplashScreen.this, TimelineActivity.class));
                finish();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    });

}
