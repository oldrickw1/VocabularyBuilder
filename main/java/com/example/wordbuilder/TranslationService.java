package com.example.wordbuilder;

import android.util.Log;

import com.deepl.api.TextResult;
import com.deepl.api.Translator;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TranslationService {

    public static String translate(String foreignWord) {

        ExecutorService executor = Executors.newFixedThreadPool(4);
        Future<TextResult> futureResult = (Future<TextResult>) executor.submit(() -> {
            TextResult result;
            Translator translator = new Translator(API.KEY);
            try {
                result = translator.translateText(foreignWord, null, "en");
            } catch (Exception e) {
                throw new Exception(e);
            }
            return result;
        });
        String result = null;
        try {
            result = futureResult.get().getText();
        } catch (Exception e) {
            e.printStackTrace();
        }

        executor.shutdown();

        Log.i("TranslationService", "TRANSLATION IS  :" + result);
        return result;
    }
}
