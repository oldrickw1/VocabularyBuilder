package com.example.wordbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Practice extends AppCompatActivity {

    private static final String TAG = "MyApp_Practice";
    Button btn_option1;
    Button btn_option2;
    Button btn_option3;
    List<Button> btnList;
    List<ImageView> heartList;
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

        //TODO: buttons all have random words, but one should always be the correct one. Should also be unique
        for (Button btn : btnList) {
            btn.setOnClickListener(v -> {
                if (checkAnswer(btn.getText().toString())) {
                    Toast.makeText(this, "CORRECT!!!", Toast.LENGTH_SHORT).show();
                    incrementScore();
                }
                else {
                    Toast.makeText(this, "Your ans: "+ btn.getText().toString() + " Ans: " + word, Toast.LENGTH_SHORT).show();
                    takeALife();
                }
                setNewQuiz();
            });
        }

    }

    private void takeALife() {
        if (currentHeart == 1) {
            gameOver();
        }
        heartList.get(--currentHeart).setVisibility(View.INVISIBLE);
    }

    //TODO: Implement gameOver()
    private void gameOver() {
        Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT).show();
    }

    private void setNewQuiz() {
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

        for (Button btn : btnList) {
            btn.setText(getRandomWord().getTargetWord());
        }
    }


    private void setToBePracticed() {
        word = getRandomWord();
        TextView toBePracticed = findViewById(R.id.tv_toBePracticed);
        toBePracticed.setText(word.getForeignWord());
    }

    private Translation getRandomWord() {
        return MyApplication.translations.get( random.nextInt(MyApplication.translations.size()));
    }

    private void resetHearts() {
        ImageView iv_heart1 = findViewById(R.id.heart1);
        ImageView iv_heart2 = findViewById(R.id.heart2);
        ImageView iv_heart3 = findViewById(R.id.heart3);
        heartList = new ArrayList<>(Arrays.asList(iv_heart1, iv_heart2, iv_heart3));
        currentHeart = heartList.size();
    }

}