package com.example.wordbuilder;

import android.util.Log;

import com.deepl.api.TextResult;
import com.deepl.api.Translator;

public class TranslationService {

    public static Translation translate(String foreignWord) {
        Thread newThread = new Thread(() -> {
            TextResult result = null;
            Translator translator = new Translator(API.KEY);
            try {
                result = translator.translateText("Hello, world!", null, "fr");

            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

        });
        newThread.start();

        return null;
    }


}
