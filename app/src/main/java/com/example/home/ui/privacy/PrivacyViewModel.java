package com.example.home.ui.privacy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PrivacyViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public PrivacyViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is FAQ fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}