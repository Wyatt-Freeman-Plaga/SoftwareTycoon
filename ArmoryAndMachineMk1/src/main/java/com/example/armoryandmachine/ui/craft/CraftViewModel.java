package com.example.armoryandmachine.ui.craft;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.armoryandmachine.R;

public class CraftViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private static int totalHeat = 0;

    public CraftViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is craft fragment");
        totalHeat = 0;
    }

    public static void addTotalHeat() {
        totalHeat++;
    }

    public static int getTotalHeat() {
        return totalHeat;
    }


    public LiveData<String> getText() {
        return mText;
    }
}