package com.example.wordbuilder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WordLibrary extends AppCompatActivity {
    private List<Translation> translations = new ArrayList<>();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_library);

        databaseHelper = MyApplication.databaseHelper;

        recyclerView = findViewById(R.id.rv_translations);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new recycleViewAdapter(databaseHelper.getTranslations(), this);
        recyclerView.setAdapter(adapter);

    }
}