package com.example.armoryandmachine;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.gson.Gson;

public class LoadoutViewModel extends AndroidViewModel {

    Tool tool1 = new Tool();
    Tool tool2 = new Tool();
    Tool tool3 = new Tool();
    Tool tool4 = new Tool();
    Tool tool5 = new Tool();


    public LoadoutViewModel(@NonNull Application application) {
        super(application);
    }

    public Tool getFirstAvailableTool() {
        if(tool1.occupied == false) {
            tool1.occupied = true;
            return tool1;
        }
        if(tool2.occupied == false && tool2.visible == true) {
            tool2.occupied = true;
            return tool2;
        }
        if(tool3.occupied == false && tool3.visible == true) {
            tool3.occupied = true;
            return tool3;
        }
        if(tool4.occupied == false && tool4.visible == true) {
            tool4.occupied = true;
            return tool4;
        }
        return tool5;
    }

    public void addTool(String toolName) {
        getFirstAvailableTool().name = toolName;
    }

    public void saveLoadoutData() {
        Context context = getApplication().getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences("master", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
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

    public void loadLoadoutData() {
        Context context = getApplication().getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences("master", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonTool1 = sharedPref.getString("tool1", "");
        tool1 = gson.fromJson(jsonTool1, Tool.class);
        String jsonTool2 = sharedPref.getString("tool2", "");
        tool2 = gson.fromJson(jsonTool2, Tool.class);
        String jsonTool3 = sharedPref.getString("tool3", "");
        tool3 = gson.fromJson(jsonTool3, Tool.class);
        String jsonTool4 = sharedPref.getString("tool4", "");
        tool4 = gson.fromJson(jsonTool4, Tool.class);
    }
}
