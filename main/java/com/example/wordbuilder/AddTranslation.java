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

            for (EditText et: Arrays.asList(et_translation1, et_translation2, et_translation3)) {
                if (!isEmpty(et)) {
                    translationList.add(et.getText().toString().trim().toLowerCase());
                }
            }

            if (translationList.isEmpty()) {
                return;
            }
            MyApplication.databaseHelper.addOne(new Translation(Language.SPANISH, Language.ENGLISH, et_foreignWord.getText().toString().trim().toLowerCase(), translationList));
            MyApplication.resetTranslations();
            startActivity(new Intent(this, MainActivity.class));
        });
    }

    public static boolean isEmpty(EditText editText) {
        return TextUtils.isEmpty(editText.getText().toString());
    }
}