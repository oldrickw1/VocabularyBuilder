package com.example.wordbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn_goToLib;
    Button btn_practice;
    Button btn_addWord;
    Button btn_scanWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_practice = findViewById(R.id.btn_practice);
        btn_goToLib = findViewById(R.id.btn_goToLib);
        btn_addWord = findViewById(R.id.btn_addTranslation);
        btn_scanWords = findViewById(R.id.btn_scanWords);
        MyApplication.resetTranslations();

        btn_goToLib.setOnClickListener(v -> {
            Intent intent = new Intent(this, WordLibrary.class);
            startActivity(intent);
        });

        btn_practice.setOnClickListener(v -> {
            Intent intent = new Intent(this, Practice.class);
            startActivity(intent);
        });

        btn_addWord.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddTranslation.class);
            startActivity(intent);
        });

        btn_scanWords.setOnClickListener(v -> {
//            Intent intent = new Intent(this, WordScan.class);
//            startActivity(intent);
        });

    }
}