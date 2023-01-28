package com.example.wordbuilder;

import java.util.List;

public class Translation {
    private Language targetLanguage;
    private Language foreignLanguage;
    private String foreignWord;
    private String targetLanguageTranslation;
    private List<String> targetLanguageTranslations;
    private boolean isBasic;
    private LevelOfMastery levelOfMastery;

    public Translation(Language foreignLanguage, Language targetLanguage, String foreignWord, List<String> targetLanguageTranslations) {
        this.foreignLanguage = foreignLanguage;
        this.targetLanguage = targetLanguage;
        this.foreignWord = foreignWord;
        this.targetLanguageTranslations = targetLanguageTranslations;
        levelOfMastery = LevelOfMastery.NOOB;
    }

    public Translation(String foreignWord, String targetLanguageTranslation) {
        this.foreignWord = foreignWord;
        this.targetLanguageTranslation = targetLanguageTranslation;

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

    public String getTargetLanguageTranslation() {
        return targetLanguageTranslation;
    }
    @Override
    public String toString() {
        return "Translation{" +
                "foreignWord='" + foreignWord + '\'' +
                ", targetLanguageTranslation='" + targetLanguageTranslation + '\'' +
                ", targetLanguageTranslations=" + targetLanguageTranslations +
                ", isBasic=" + isBasic +
                ", levelOfMastery=" + levelOfMastery +
                '}';
    }
}
