package com.example.wordbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Practice extends AppCompatActivity {
    //TODO when a word is guessed correctly, update the level of mastery. Use the level of mastery as a condition for words to practice
    //TODO: there's a big design flaw! Each and every possible translation should be accepted. Now, only unique translations are evaluated. The getTranslations method in the DatabaseHelper needs to be modified to include a list of related translations;
    /*
        Elaboration on  the above:
        This is more complicated than I anticipated. What I need, is a list of possible translations, which could be a list of hashMaps with a
        foreign word as key and list of translations as values. One of those possible translations should then be selected randomly, and assigned
        to a random button. The other 2 buttons then should get a text value of any target word except for those in the possible translations.
        (I also need an update functionality if I want to add a possible translation to an
        existing foreign word; see DatabaseHelper).

        For now, I'll leave it and focus on the rest. The current program runs, but some correct answers will be signed as incorrect.
     */

    private static final String TAG = "MyApp_Practice";
    List<Button> btnList;
    List<ImageView> heartList;
    List<Translation> translations;
    int currentHeart;
    Random random = new Random();
    Translation word;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        btnList = Arrays.asList(findViewById(R.id.btn_option1), findViewById(R.id.btn_option2), findViewById(R.id.btn_option3));
        resetHearts();
        setNewQuiz();

        for (Button btn : btnList) { btn.setOnClickListener(this::guessWord); }
    }

    private void guessWord(View view) {
        Button guess = (Button) view;
        if (checkAnswer(guess.getText().toString())) {
            incrementScore();
        }
        else {
            displayMistake(guess);
            takeALife();
        }
        setNewQuiz();
    }

    private void displayMistake(Button guess) {
        new WrongAnswerDialog(guess.getText().toString(), word).show(getSupportFragmentManager(), "Wrong Answer");
    }

    private void takeALife() {
        if (currentHeart == 1) {
            startActivity(new Intent(this, GameOver.class));
        }
        heartList.get(--currentHeart).setVisibility(View.INVISIBLE);
    }

    private void setNewQuiz() {
        translations = MyApplication.uniqueTranslationList;
        setToBePracticed();
        setOptions();
    }

    private boolean checkAnswer(String option) {
        Log.i(TAG, "Option: " + option + ". Word: " + word.getForeignWord() + ". CorrectAnswer: " + word.getTargetWord());
        return word.getTargetWord().equals(option);
    }

    private void incrementScore() {
        TextView tv_score = findViewById(R.id.tv_score);
        tv_score.setText(Integer.toString(++score));
    }

    private void setOptions() {
        btnList.get(random.nextInt(btnList.size())).setText(word.getTargetWord());
        // skip the button with the correct answer
        for (Button btn : btnList) {
            if (TextUtils.isEmpty(btn.getText())) {
                btn.setText(getRandomWord().getTargetWord());
            }
        }
    }

    private void setToBePracticed() {
        word = getRandomWord();
        TextView toBePracticed = findViewById(R.id.tv_toBePracticed);
        toBePracticed.setText(word.getForeignWord());
    }

    private Translation getRandomWord() {
        int randIndex = random.nextInt(translations.size());
        return translations.size() < 3 ? translations.get(randIndex) : translations.remove(randIndex);
    }

    private void resetHearts() {
        heartList = new ArrayList<>(Arrays.asList(findViewById(R.id.heart1), findViewById(R.id.heart2), findViewById(R.id.heart3)));
        currentHeart = heartList.size();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }

}