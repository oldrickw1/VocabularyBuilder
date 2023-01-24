package com.example.wordbuilder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private final String TAG = "MyApp.DatabaseHelper";
    private static final String FOREIGN_LANGUAGE_COLUMN = "FOREIGN_LANGUAGE";
    private static final String TARGET_LANGUAGE_COLUMN = "TARGET_LANGUAGE";
    private static final String FOREIGN_ID_COLUMN = "FOREIGN_ID";
    private static final String TARGET_ID_COLUMN = "TARGET_ID";

    private static String FOREIGN_TABLE;
    private static String TARGET_TABLE;
    private static String JOIN_TABLE;

    private String foreignLanguage;
    private String targetLanguage;


    public DatabaseHelper(@Nullable Context context, String foreignLanguage, String targetLanguage) {
        super(context,  foreignLanguage.substring(0,1).toLowerCase() + foreignLanguage.substring(1).toLowerCase() + "To" + targetLanguage.substring(0,1).toLowerCase() + targetLanguage.substring(1).toLowerCase() + ".db" , null, 1);
        this.foreignLanguage = foreignLanguage.substring(0,1).toLowerCase() + foreignLanguage.substring(1).toLowerCase();
        this.targetLanguage = targetLanguage.substring(0,1).toLowerCase() + targetLanguage.substring(1).toLowerCase();
        FOREIGN_TABLE = foreignLanguage + "Words";
        TARGET_TABLE = targetLanguage + "Words";
        JOIN_TABLE = this.foreignLanguage + "To" + this.targetLanguage;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createForeignWordTableStatement =
                "CREATE TABLE " + FOREIGN_TABLE +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + FOREIGN_LANGUAGE_COLUMN + " TEXT)";
        String createTargetLanguageWordTableStatement = "CREATE TABLE " + TARGET_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + TARGET_LANGUAGE_COLUMN + " TEXT)";
        String createLinkedTableStatement ="CREATE TABLE " + JOIN_TABLE + " (" + FOREIGN_ID_COLUMN + " INT, " + TARGET_ID_COLUMN + " INT)";

        db.execSQL(createForeignWordTableStatement);
        db.execSQL(createTargetLanguageWordTableStatement);
        db.execSQL(createLinkedTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    // TODO: REFACTOR BY USING LAST_INSERT_ID TO GET THE GENERATED ID'S.
    public void addOne(Translation translation) {
        SQLiteDatabase db = this.getWritableDatabase();

        // inserting into foreign language table
        ContentValues cvForeign = new ContentValues();
        cvForeign.put(FOREIGN_LANGUAGE_COLUMN, translation.getForeignWord());
        db.insert(FOREIGN_TABLE, null, cvForeign);

        // inserting into target language table
        for (String uniqueTranslation : translation.getTargetLanguageTranslations()) {
            ContentValues cvTarget = new ContentValues();
            cvTarget.put(TARGET_LANGUAGE_COLUMN, uniqueTranslation);
            db.insert(TARGET_TABLE, null, cvTarget);
        }

        // inserting into join table
        // we need the 2 foreign id's. Let's read them from db
        int foreignId = getForeignId(translation, db);
        List<Integer> targetIdList = getTargetIdList(translation, db);

        // now finally we can insert to the linked table
        for (int targetId : targetIdList) {
            ContentValues cv = new ContentValues();
            cv.put(FOREIGN_ID_COLUMN, foreignId);
            cv.put(TARGET_ID_COLUMN, targetId);
            db.insert(JOIN_TABLE, null, cv);
        }
        db.close();
    }

    @NonNull
    private List<Integer> getTargetIdList(Translation translation, SQLiteDatabase db) {
        List<Integer> targetIdList = new ArrayList<>();
        for (String uniqueTranslation : translation.getTargetLanguageTranslations()) {
            String query = "SELECT ID FROM " + TARGET_TABLE + " WHERE " + TARGET_LANGUAGE_COLUMN + "='" + uniqueTranslation + "'";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                targetIdList.add(cursor.getInt(0));
            }
            cursor.close();
        }
        return targetIdList;
    }

    private int getForeignId(Translation translation, SQLiteDatabase db) {
        Cursor foreignIdCursor = db.rawQuery("SELECT ID FROM " + FOREIGN_TABLE + " WHERE " + FOREIGN_LANGUAGE_COLUMN + "='" + translation.getForeignWord() + "'", null);
        int foreignId = foreignIdCursor.moveToFirst() ? foreignIdCursor.getInt(0) :-1;
        foreignIdCursor.close();
        return foreignId;
    }

    public List<Translation> getTranslations() {
        SQLiteDatabase db = getReadableDatabase();

        List<Translation> translationList = new ArrayList<>();
        String queryString = "SELECT " + FOREIGN_TABLE + "." + FOREIGN_LANGUAGE_COLUMN + ", " + TARGET_TABLE + "." + TARGET_LANGUAGE_COLUMN + " FROM " + JOIN_TABLE + " INNER JOIN"+
                " " + FOREIGN_TABLE + " ON " + JOIN_TABLE + "." + FOREIGN_ID_COLUMN + " = " + FOREIGN_TABLE + "." + "ID " + " INNER JOIN" +
                " " + TARGET_TABLE + " ON " + JOIN_TABLE + "." + TARGET_ID_COLUMN + " = " + TARGET_TABLE + "." + "ID ";

        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                String foreignLanguageWord = cursor.getString(0);
                String targetLanguageTranslation = cursor.getString(1);
                Log.i(TAG, "foreignLanguageWord: " + foreignLanguageWord + ", targetLanguageTranslation: " + targetLanguageTranslation);
                translationList.add(new Translation(foreignLanguageWord, targetLanguageTranslation));
            } while (cursor.moveToNext());
        } else {
            // list is empty
        }
        cursor.close();
        db.close();
        Log.i(TAG, "translationList: " + translationList.get(0).toString());
        return translationList;
    }
}
