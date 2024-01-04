package com.icanerdogan.posedetection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivityMaster extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_master);


            ImageButton button1 = findViewById(R.id.button1);
            ImageButton button2 = findViewById(R.id.button2);
            ImageButton button3 = findViewById(R.id.button3);
            ImageButton button4 = findViewById(R.id.button4);
            ImageButton button5 = findViewById(R.id.button5);


            button1.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }); //object_detection2 클릭 리스너


            button2.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), MainActivityFace.class);
                startActivity(intent);
            }); //face_detection 클릭 리스너


            button3.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), MainActivityText.class);
                startActivity(intent);
            });  //text_detection 클릭 리스너



            button4.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), MainActivityWebview.class);
                startActivity(intent);
            }); //AI GYM 리스너

            button5.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), MainActivityAr.class);
                startActivity(intent);
            }); //ar 리스너


    }

}

