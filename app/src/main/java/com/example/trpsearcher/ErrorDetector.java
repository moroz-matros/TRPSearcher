package com.example.trpsearcher;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trpsearcher.activities.AuthActivity;

public class ErrorDetector {

    public boolean lengthCheckMax(EditText editText, Integer n){
        if (editText.getText().length() >= n){
            editText.setError("Поле должно содержать максимум символов: " + n.toString());
            return false;
        }
        else return true;
    }

    public boolean lengthCheckMin(EditText editText, Integer n){
        if (editText.getText().length() < n) {
            editText.setError("Поле должно содержать минимум символов: " + n.toString());
            return false;
        }
        else return true;
    }

    public boolean isNotEmpty(EditText editText){
        if (editText.getText().length() == 0){
            editText.setError("Заполните поле");
            return false;
        }
        else return true;

    }




}
