package com.example.home.ui.location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LocationViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public LocationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is FAQ fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
