package com.example.armoryandmachine.ui.craft;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.armoryandmachine.R;

public class CraftViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CraftViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is craft fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}