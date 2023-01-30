package com.example.wordbuilder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class WrongAnswerDialog extends AppCompatDialogFragment {
    String guess;
    Translation answer;

    public WrongAnswerDialog(String guess, Translation answer) {
        this.guess = guess;
        this.answer = answer;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Wrong Answer for \"" + answer.getForeignWord()+"\"").
                setMessage("Your answer:        " + guess + "\nCorrect answer:   " + answer.getTargetWord()).
                setPositiveButton("OK", (DialogInterface dialog, int which) -> {

                });
        return builder.create();
    }
}
