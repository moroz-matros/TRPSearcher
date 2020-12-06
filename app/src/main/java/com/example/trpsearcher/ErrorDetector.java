package com.example.trpsearcher;

import android.widget.EditText;
import android.widget.TextView;

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

    public boolean lengthCheckMaxText(EditText editText){
        if (editText.getText().length() > Math.pow(2, 16)) {
            editText.setError("Превышен лимит символов (2^16)");
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

    public boolean isNotEmpty(TextView textView) {
        if (textView.getText().length() == 0) {
            textView.setError("Заполните поле");
            return false;
        } else return true;
    }

}
