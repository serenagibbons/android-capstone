package com.example.androidcapstone.ui.public_feed;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PublicFeedViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PublicFeedViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Public Feed fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}