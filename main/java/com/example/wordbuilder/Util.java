package com.example.wordbuilder;

import android.content.Context;
import android.widget.Toast;

public class Util {
    private Util(){};
    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
