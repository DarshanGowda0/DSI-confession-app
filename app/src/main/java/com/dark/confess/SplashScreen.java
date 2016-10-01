package com.dark.confess;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        thread.start();

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
