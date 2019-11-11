package com.example.home.ui.your_reports;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class YourReportsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public YourReportsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Your Reports fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
