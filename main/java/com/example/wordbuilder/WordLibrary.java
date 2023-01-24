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

//        fillTranslationsWithDummyData();
        databaseHelper = MyApplication.databaseHelper;

        recyclerView = findViewById(R.id.rv_translations);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new recycleViewAdapter(databaseHelper.getTranslations() , this);
        recyclerView.setAdapter(adapter);

    }

//    private void fillTranslationsWithDummyData(){
//        Translation t1 = new Translation("casa", Arrays.asList("house"));
//        Translation t2 = new Translation("bomba", Arrays.asList("pump", "bomb"));
//        Translation t3 = new Translation("el", Arrays.asList("the"));
//        Translation t4 = new Translation("la", Arrays.asList("the"));
//        Translation t5 = new Translation("zapato", Arrays.asList("shoe"));
//        Translation t6 = new Translation("mesa", Arrays.asList("table"));
//
//        translations.addAll(Arrays.asList(t1,t2,t3,t4,t5,t6));
//
//    }
}