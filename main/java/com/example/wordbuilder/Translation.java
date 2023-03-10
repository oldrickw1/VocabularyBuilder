package com.example.wordbuilder;

import java.util.ArrayList;
import java.util.List;

public class Translation {
    private Language targetLanguage;
    private Language foreignLanguage;
    private String foreignWord;
    private String targetWord;
    private List<String> targetLanguageTranslations;
    private boolean isBasic;
    private LevelOfMastery levelOfMastery;

    public Translation(Language foreignLanguage, Language targetLanguage, String foreignWord, List<String> targetLanguageTranslations) {
        this.foreignLanguage = foreignLanguage;
        this.targetLanguage = targetLanguage;
        this.foreignWord = foreignWord;
        this.targetLanguageTranslations = targetLanguageTranslations;
        levelOfMastery = LevelOfMastery.BEGINNER;
    }

    public Translation(String foreignWord, String targetWord) {
        this.foreignWord = foreignWord.toLowerCase().trim();
        this.targetWord = targetWord.toLowerCase().trim();
    }

    public String getForeignWord() {
        return foreignWord;
    }


    public List<String> getTargetLanguageTranslations() {
        return targetLanguageTranslations;
    }


    public Language getTargetLanguage() {
        return targetLanguage;
    }


    public Language getForeignLanguage() {
        return foreignLanguage;
    }



    public String getTargetWord() {
        return targetWord;
    }


}
