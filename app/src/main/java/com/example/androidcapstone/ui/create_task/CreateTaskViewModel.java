package com.example.androidcapstone.ui.create_task;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateTaskViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public CreateTaskViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is create task fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
