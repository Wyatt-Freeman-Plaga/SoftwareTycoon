package com.example.armoryandmachine.ui.jobs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class JobsViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    public int highestUnlocked = 100;

    public JobsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is explore fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}