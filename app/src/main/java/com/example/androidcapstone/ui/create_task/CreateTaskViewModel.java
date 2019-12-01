package com.example.androidcapstone.ui.create_task;

import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateTaskViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    EditText editText;
    public CreateTaskViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is create task fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
