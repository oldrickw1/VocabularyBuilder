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

//TODO: Languages are not dynamic. They are typed as constants in the code. Needs to be changed by looking up user settings (do this in MyApplication)
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

            if (isEmpty(et_foreignWord)) {
                return;
            }
            String foreignWord = format(extractStringFromEditText(et_foreignWord));

            for (EditText et: Arrays.asList(et_translation1, et_translation2, et_translation3)) {
                if (!isEmpty(et)) {
                    translationList.add(et.getText().toString().trim().toLowerCase());
                }
                if (translationList.isEmpty()) {
                    translationList.add(TranslationService.translate(foreignWord));
                }
            }

            if (translationList.isEmpty()) {
                return;
            }
            MyApplication.databaseHelper.addOne(new Translation(Language.SPANISH, Language.ENGLISH, foreignWord, translationList));
//            MyApplication.resetTranslations();
            startActivity(new Intent(this, MainActivity.class));
        });
    }

    private String extractStringFromEditText(EditText et_foreignWord) {
        return et_foreignWord.getText().toString();
    }

    public static boolean isEmpty(EditText editText) {
        return TextUtils.isEmpty(editText.getText().toString());
    }

    private String format(String rawString) {
        return rawString.trim().toLowerCase();
    }



}