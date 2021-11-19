package com.example.project1_hacktiv8;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class splashScreen extends AppCompatActivity {


    private static int splashInterval = 2000;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().setTitle("ToDoList App");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(splashScreen.this, MainActivity.class);
                startActivity(i);

                this.finish();
            }

            private void finish() {
                Intent i = new Intent(splashScreen.this, MainActivity.class);
                startActivity(i);
            }
        },splashInterval);
    }
}