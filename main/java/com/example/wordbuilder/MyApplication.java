package com.example.wordbuilder;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

    public static DatabaseHelper databaseHelper;
    public static Language activeForeignLanguage = Language.SPANISH;
    public static Language activeTargetLanguage = Language.ENGLISH;

    public MyApplication() {
        databaseHelper = new DatabaseHelper(this);
    }
}
