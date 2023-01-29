    package com.example.wordbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class GameOver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);


        findViewById(R.id.btn_tryAgain).setOnClickListener(view -> {
            startActivity(new Intent(this, Practice.class));
        });
    }
}