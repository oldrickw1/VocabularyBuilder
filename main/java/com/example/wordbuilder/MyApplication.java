package com.example.wordbuilder;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

    public static DatabaseHelper databaseHelper;

    public MyApplication() {
        databaseHelper = new DatabaseHelper(this, "Spanish", "English");
    }
}
