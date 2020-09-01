package com.example.armoryandmachine;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.gson.Gson;

public class JobViewModel extends AndroidViewModel {

    public double playerHealth = 100;
    Tool tool1;
    Tool tool2;
    Tool tool3;
    Tool tool4;
    boolean tool2Visible;
    boolean tool3Visible;
    boolean tool4Visible;

    public JobViewModel(@NonNull Application application) {
        super(application);
    }

    public synchronized void reduceTotalHealth(int damage) {
        playerHealth -= damage;
        if(playerHealth < 0) {
            playerHealth = 0;
        }
    }

    public double getPortionHealth() {
        return playerHealth;
    }

    public boolean isPlayerAlive() {
        if(playerHealth > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void saveLoadoutData() {
        Context context = getApplication().getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences("master", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putBoolean("tool2Visible", tool2Visible);
        editor.putBoolean("tool3Visible", tool3Visible);
        editor.putBoolean("tool4Visible", tool4Visible);
        Gson gson = new Gson();
        String jsonTool1 = gson.toJson(tool1);
        editor.putString("tool1", jsonTool1);
        String jsonTool2 = gson.toJson(tool2);
        editor.putString("tool2", jsonTool2);
        String jsonTool3 = gson.toJson(tool3);
        editor.putString("tool3", jsonTool3);
        String jsonTool4 = gson.toJson(tool4);
        editor.putString("tool4", jsonTool4);
        editor.commit();
    }

    public void loadData() {
        Context context = getApplication().getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences("master", Context.MODE_PRIVATE);
        tool2Visible = sharedPref.getBoolean("tool2Visible", false);
        tool3Visible = sharedPref.getBoolean("tool3Visible", false);
        tool4Visible = sharedPref.getBoolean("tool4Visible", false);
        Gson gson = new Gson();
        String jsonTool1 = sharedPref.getString("tool1", "");
        Tool tool1 = gson.fromJson(jsonTool1, Tool.class);
        String jsonTool2 = sharedPref.getString("tool2", "");
        Tool tool2 = gson.fromJson(jsonTool2, Tool.class);
        String jsonTool3 = sharedPref.getString("tool3", "");
        Tool tool3 = gson.fromJson(jsonTool3, Tool.class);
        String jsonTool4 = sharedPref.getString("tool4", "");
        Tool tool4 = gson.fromJson(jsonTool4, Tool.class);
    }
}
