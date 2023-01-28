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
import java.util.HashMap;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private final String TAG = "MyApp.DatabaseHelper";

    public DatabaseHelper(@Nullable Context context) {
        super(context,  "translations.db" , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createLinkedTableStatement ="CREATE TABLE TRANSLATIONS (ID INTEGER PRIMARY KEY AUTOINCREMENT, FOREIGN_ID_COLUMN INT, TARGET_ID_COLUMN INT)";
        db.execSQL(createLinkedTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}


    public void addOne(Translation translation) {
        SQLiteDatabase db = this.getWritableDatabase();

        String foreignTableName = getOrCreateTable(translation.getForeignLanguage(), db);
        String targetTableName = getOrCreateTable(translation.getTargetLanguage(), db);

        // inserting into foreign language table
        ContentValues cvForeign = new ContentValues();
        cvForeign.put("WORD", translation.getForeignWord());
        long foreignId = db.insert(foreignTableName, null, cvForeign);

        // inserting into target language table
        List<Long> targetIdList = new ArrayList<>();
        for (String uniqueTranslation : translation.getTargetLanguageTranslations()) {
            ContentValues cvTarget = new ContentValues();
            cvTarget.put("WORD", uniqueTranslation);
            targetIdList.add(db.insert(targetTableName, null, cvTarget));
        }

        // now we can insert to the linked table
        for (long targetId : targetIdList) {
            ContentValues cv = new ContentValues();
            cv.put("FOREIGN_ID_COLUMN", foreignId);
            cv.put("TARGET_ID_COLUMN", targetId);
            db.insert("TRANSLATIONS", null, cv);
        }
        db.close();
    }

    private String getOrCreateTable(Language language, SQLiteDatabase db) {
        String tableName = language.toString();
        Log.i(TAG, "CREATING A NEW TABLE");
        String createStatement = "CREATE TABLE IF NOT EXISTS " + tableName + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, WORD TEXT UNIQUE);";
        db.execSQL(createStatement);
        return tableName;
    }

    public List<Translation> getTranslations() {
        SQLiteDatabase db = getReadableDatabase();

        List<Translation> translationList = new ArrayList<>();

        String foreignTable = MyApplication.activeForeignLanguage.toString();
        String targetTable = MyApplication.activeTargetLanguage.toString();

        String queryString = "SELECT " + foreignTable + ".WORD, " +  targetTable + ".WORD FROM TRANSLATIONS INNER JOIN"+
                " " + foreignTable + " ON TRANSLATIONS.FOREIGN_ID_COLUMN = " + foreignTable + ".ID INNER JOIN"+
                " " + targetTable + " ON TRANSLATIONS.TARGET_ID_COLUMN = " + targetTable + ".ID";

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
