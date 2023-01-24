package com.example.wordbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddTranslation extends AppCompatActivity {
    private final String TAG = "MyApp.AddTranslation";
    Button btn_submitTranslation;
    EditText et_foreignWord;
    EditText et_translation1;
    EditText et_translation2;
    EditText et_translation3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_translation);

        btn_submitTranslation = findViewById(R.id.btn_submitTranslation);
        et_foreignWord = findViewById(R.id.et_foreignWord);
        et_translation1 = findViewById(R.id.et_translation1);
        et_translation2 = findViewById(R.id.et_translation2);
        et_translation3 = findViewById(R.id.et_translation3);

        btn_submitTranslation.setOnClickListener(v -> {
            List<String> translationList = new ArrayList<>();
            if (TextUtils.isEmpty(et_translation1.getText().toString())) {
                return;
            }
            translationList.add(et_translation1.getText().toString());
            if (!TextUtils.isEmpty(et_translation2.getText().toString())) {
                translationList.add(et_translation2.getText().toString());
            }
            if (!TextUtils.isEmpty(et_translation3.getText().toString())) {
                translationList.add(et_translation3.getText().toString());
            }
            MyApplication.databaseHelper.addOne(new Translation(et_foreignWord.getText().toString(), translationList));
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });




    }
}