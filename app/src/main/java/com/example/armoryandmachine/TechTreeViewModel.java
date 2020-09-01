package com.example.armoryandmachine;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TechTreeViewModel extends AndroidViewModel {

    public boolean frontendUnlocked = false;
    public boolean designUnlocked = false;
    public boolean salesUnlocked = false;

    private int backendWorkers;
    private int backendWorkerCap;

    private int frontendWorkers;
    private int frontendWorkerCap;

    private int designWorkers;
    private int designWorkerCap;

    private int salesWorkers;
    private int salesWorkerCap;

    public float totalBackend = 0;
    public float totalFrontend = 0;
    public float totalDesign = 0;
    public float totalSales = 0;

    private int backendCap = 20;
    private int frontendCap = 20;
    private int designCap = 20;
    private int salesCap = 20;

    private float baseBackend = 0;
    private float baseFrontend = 0;
    private float baseDesign = 0;
    private float baseSales = 0;
    private long baseTime = 0;

    private double baseRate = .0005;
    private double backendRate = .0005; //.0005
    private double frontendRate = .0005;
    private double designRate = .0005;
    private double salesRate = .0005;

    public TechTreeViewModel(Application application) {
        super(application);
    }

    public boolean unlockFrontend(Context context) {
        loadData();
        System.out.println("backend read as " + totalBackend);
        if(frontendUnlocked == true) {
            System.out.println("frontendUnlocked already seen as true");
            return true;
        }
        if(100 <= totalBackend) {
            System.out.println("Backend high enough to buy");
            frontendUnlocked = true;
            totalBackend -= 100;
            saveData();
            return true;
        } else {
            System.out.println("failed to buy");
            return false;
        }
    }

    public boolean unlockDesign(Context context) {
        loadData();
        if(designUnlocked == true) {
            return true;
        }
        if(100 <= totalFrontend) {
            designUnlocked = true;
            totalFrontend -= 100;
            saveData();
            return true;
        } else {
            return false;
        }
    }

    public boolean unlockSales(Context context) {
        loadData();
        if(salesUnlocked == true) {
            return true;
        }
        if(100 <= totalDesign) {
            salesUnlocked = true;
            totalDesign -= 100;
            saveData();
            return true;
        } else {
            return false;
        }
    }


    public void saveData() {
        Context context = getApplication().getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences("master", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong("latestTimeKey", System.currentTimeMillis());
        editor.putInt("latestBackendWorkers", backendWorkers);
        editor.putInt("latestFrontendWorkers", frontendWorkers);
        editor.putInt("latestDesignWorkers", designWorkers);
        editor.putInt("latestSalesWorkers", salesWorkers);
        editor.putFloat("latestBackendBase", totalBackend);
        editor.putFloat("latestFrontendBase", totalFrontend);
        editor.putFloat("latestDesignBase", totalDesign);
        editor.putFloat("latestSalesBase", totalSales);
        editor.putInt("backendCap", backendCap);
        editor.putInt("frontendCap", frontendCap);
        editor.putInt("designCap", designCap);
        editor.putInt("salesCap", salesCap);
        editor.putInt("backendWorkerCap", backendWorkerCap);
        editor.putInt("frontendWorkerCap", frontendWorkerCap);
        editor.putInt("designWorkerCap", designWorkerCap);
        editor.putInt("salesWorkerCap", salesWorkerCap);
        editor.putFloat("backendRate", (float) backendRate);
        editor.putFloat("frontendRate", (float) frontendRate);
        editor.putFloat("designRate", (float) designRate);
        editor.putFloat("salesRate", (float) salesRate);
        editor.putBoolean("frontendUnlocked", frontendUnlocked);
        editor.putBoolean("designUnlocked", designUnlocked);
        editor.putBoolean("salesUnlocked", salesUnlocked);
        editor.commit();
    }

    public void loadData() {
        Context context = getApplication().getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences("master", Context.MODE_PRIVATE);
        baseTime = sharedPref.getLong("latestTimeKey", 1);
        totalBackend = sharedPref.getFloat("latestBackendBase", 0);
        totalFrontend = sharedPref.getFloat("latestFrontendBase", 0);
        totalDesign = sharedPref.getFloat("latestDesignBase", 0);
        totalSales = sharedPref.getFloat("latestSalesBase", 0);
        backendCap = sharedPref.getInt("backendCap", 20);
        frontendCap = sharedPref.getInt("frontendCap", 20);
        designCap = sharedPref.getInt("designCap", 20);
        salesCap = sharedPref.getInt("salesCap", 20);
        backendWorkers = sharedPref.getInt("latestBackendWorkers", 0);
        frontendWorkers = sharedPref.getInt("latestFrontendWorkers", 0);
        designWorkers = sharedPref.getInt("latestDesignWorkers", 0);
        salesWorkers = sharedPref.getInt("latestSalesWorkers", 0);
        backendWorkerCap = sharedPref.getInt("backendWorkerCap", 1);
        frontendWorkerCap = sharedPref.getInt("frontendWorkerCap", 1);
        designWorkerCap = sharedPref.getInt("designWorkerCap", 1);
        salesWorkerCap = sharedPref.getInt("salesWorkerCap", 1);
        backendRate = sharedPref.getFloat("backendRate", (float) 0.0005);
        frontendRate = sharedPref.getFloat("frontendRate", (float) 0.0005);
        designRate = sharedPref.getFloat("designRate", (float) 0.0005);
        salesRate = sharedPref.getFloat("salesRate", (float) 0.0005);
        frontendUnlocked = sharedPref.getBoolean("frontendUnlocked", false);
        designUnlocked = sharedPref.getBoolean("designUnlocked", false);
        salesUnlocked = sharedPref.getBoolean("salesUnlocked", false);
    }
}
