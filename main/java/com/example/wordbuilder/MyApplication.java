package com.example.wordbuilder;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

    public static DatabaseHelper databaseHelper;
    public static Language activeForeignLanguage = Language.SPANISH;
    public static Language activeTargetLanguage = Language.ENGLISH;
    public static List<Translation> translations;

    public MyApplication() {
        databaseHelper = new DatabaseHelper(this);
    }

    public static void resetTranslations() {
        translations = databaseHelper.getTranslations();
    }
}
