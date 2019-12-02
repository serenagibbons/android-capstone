package com.example.androidcapstone.ui.personal_feed;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PersonalFeedViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PersonalFeedViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Personal Feed fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }


}