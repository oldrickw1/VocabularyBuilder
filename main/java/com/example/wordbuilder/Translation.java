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
        this.foreignWord = foreignWord.toLowerCase().trim();
        List<String> formattedTranslations = new ArrayList<>();
        for (String uniqueTranslation : targetLanguageTranslations) {
            formattedTranslations.add(uniqueTranslation.toLowerCase().trim());
        }
        this.targetLanguageTranslations = formattedTranslations;
        levelOfMastery = LevelOfMastery.NOOB;
    }

    public Translation(String foreignWord, String targetWord) {
        this.foreignWord = foreignWord.toLowerCase().trim();
        this.targetWord = targetWord.toLowerCase().trim();

    }

    public String getForeignWord() {
        return foreignWord;
    }

    public void setForeignWord(String foreignWord) {
        this.foreignWord = foreignWord;
    }

    public List<String> getTargetLanguageTranslations() {
        return targetLanguageTranslations;
    }

    public void setTargetLanguageTranslations(List<String> targetLanguageTranslations) {
        this.targetLanguageTranslations = targetLanguageTranslations;
    }

    public Language getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(Language targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public Language getForeignLanguage() {
        return foreignLanguage;
    }

    public void setForeignLanguage(Language foreignLanguage) {
        this.foreignLanguage = foreignLanguage;
    }


    public String getTargetWord() {
        return targetWord;
    }

    public void setTargetWord(String targetWord) {
        this.targetWord = targetWord;
    }

    public LevelOfMastery getLevelOfMastery() {
        return levelOfMastery;
    }

    public void setLevelOfMastery(LevelOfMastery levelOfMastery) {
        this.levelOfMastery = levelOfMastery;
    }
}
