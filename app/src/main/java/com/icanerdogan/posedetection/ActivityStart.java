package com.icanerdogan.posedetection;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityStart extends AppCompatActivity {

    private static final int SPLASH_DELAY = 3000; // 3초

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // 3초 후에 다음 액티비티로 전환
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ActivityStart.this, MainActivityMaster.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DELAY);
    }
}