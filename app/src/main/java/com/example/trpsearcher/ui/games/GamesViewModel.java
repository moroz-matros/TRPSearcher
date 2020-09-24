package com.example.trpsearcher.ui.games;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GamesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GamesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Доска");
    }

    public LiveData<String> getText() {
        return mText;
    }
}