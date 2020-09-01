package com.example.armoryandmachine;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class MainViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;

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

    private float totalBackend = 0;
    private float totalFrontend = 0;
    private float totalDesign = 0;
    private float totalSales = 0;

    private int backendCap = 20;
    private int frontendCap = 20;
    private int designCap = 20;
    private int salesCap = 20;

    private float baseBackend = 0;
    private float baseFrontend = 0;
    private float baseDesign = 0;
    private float baseSales = 0;
    private long baseTime = 0;

    private double baseRate = .5;
    private double backendRate = .5; //.0005
    private double frontendRate = .0005;
    private double designRate = .0005;
    private double salesRate = .0005;

    private double frontendConsumptionFactor = 3;
    private double designComsumptionFactor = 3;
    private double salesConsumptionFactor = 3;

    public RecyclerViewAdapterCraft craftAdapter;
    public RecyclerViewAdapterResearch researchAdapter;
    public RecyclerViewAdapterJobs jobsAdapter;
    public RecyclerViewAdapterLog logAdapter;

    public MainViewModel(Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is the main fragment");
    }

    public CharSequence getFullTotalChar(int position) {
        switch (position) {
            case 0:
                return Integer.toString((int) totalBackend);
            case 1:
                return Integer.toString((int) totalFrontend);
            case 2:
                return Integer.toString((int) totalDesign);
            case 3:
                return Integer.toString((int) totalSales);
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
    }

    public synchronized int updateTotals(int position, Context context) {
        double randomID = Math.random();
        if(position != 0) {
            switch (position) {
                case 1:
                    return getTwoDecimals(totalFrontend);
                case 2:
                    return getTwoDecimals(totalDesign);
                case 3:
                    return getTwoDecimals(totalSales);
                default:
                    throw new IllegalStateException("Unexpected value: " + position);
            }
        }
        //System.out.println("start of " + randomID);
        loadData();
        for(int i=0; i<4; i++) {
            updateTotal(i, context);
        }
        //System.out.println("end of " + randomID);
        //saveData(context);
        if(TechTreeActivity.active == true) {
            System.out.println("saving");
            saveData();
        }
        switch (position) {
            case 0:
                //System.out.println(totalBackend);
                //return (int) totalBackend;
                return getTwoDecimals(totalBackend);
            case 1:
                return getTwoDecimals(totalFrontend);
            case 2:
                return getTwoDecimals(totalDesign);
            case 3:
                return getTwoDecimals(totalSales);
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
    }

    private int updateTotal(int position, Context context) {
        if(baseTime == 1) {
            baseTime = System.currentTimeMillis();
            //System.out.println("This should only show up once!");
            android.os.SystemClock.sleep(8);
        }
        long currentTime = System.currentTimeMillis();
        long timeElapsed = currentTime - baseTime;
        //System.out.println(timeElapsed + " time elapased.");
        double baseTotal = 0;
        int relevantWorkers = 0;
        int relevantCap = 0;
        double relevantRate = 0.05;
        double relevantConsumptionFactor = .0;
        float relevantRequiredResorceAvailable = (float) 0.1;
        switch (position) {
            case 0:
                baseTotal = baseBackend;
                relevantWorkers = backendWorkers;
                relevantCap = backendCap;
                relevantRate = backendRate;
                relevantConsumptionFactor = 0;
                break;
            case 1:
                baseTotal = baseFrontend;
                relevantWorkers = frontendWorkers;
                relevantCap = frontendCap;
                relevantRate = frontendRate;
                relevantConsumptionFactor = frontendConsumptionFactor;
                relevantRequiredResorceAvailable = totalBackend;
                break;
            case 2:
                baseTotal = baseDesign;
                relevantWorkers = designWorkers;
                relevantCap = designCap;
                relevantRate = designRate;
                relevantConsumptionFactor = designComsumptionFactor;
                relevantRequiredResorceAvailable = totalFrontend;
                break;
            case 3:
                baseTotal = baseSales;
                relevantWorkers = salesWorkers;
                relevantCap = salesCap;
                relevantRate = salesRate;
                relevantConsumptionFactor = salesConsumptionFactor;
                relevantRequiredResorceAvailable = totalDesign;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
        float growth = (float) (timeElapsed * relevantRate * relevantWorkers);
        float newTotal = (float) (baseTotal + growth);
        if (newTotal > relevantCap) {
            float surplus = newTotal - relevantCap;
            growth -= surplus;
            newTotal = relevantCap;
        }
        float requiredResorceNeeded = (float) ((growth) * relevantConsumptionFactor);
        float producibleTotal = 0;
        if(requiredResorceNeeded > relevantRequiredResorceAvailable) {
            producibleTotal = (float) (relevantRequiredResorceAvailable / relevantConsumptionFactor);
            if(position == 1) {
                //System.out.println("producibleTotal1 seen as " + producibleTotal);
                //System.out.println("should read same as totalBackend " + relevantRequiredResorceAvailable);
                //System.out.println("backend seen as " + totalBackend);
            }
            newTotal = producibleTotal + (float) baseTotal;
        } else {
            producibleTotal = (float) growth;
            if(position == 1) {
                //System.out.println("producibleTotal2 seen as " + producibleTotal);
            }
            //newTotal = producibleTotal + (float) baseTotal;
        }
        //System.out.println(producibleTotal + " is the producable total.");
        float relevantRequiredResorceUsed = (float) (producibleTotal * relevantConsumptionFactor);
        //System.out.println((timeElapsed * rate * relevantWorkers) + " gained.");
        //System.out.println(newTotal);
        switch (position) {
            case 0:
                totalBackend = newTotal;
                break;
            case 1:
                totalFrontend = newTotal;
                totalBackend -= relevantRequiredResorceUsed;
                //System.out.println(relevantRequiredResorceUsed + " backend used.");
                break;
            case 2:
                totalDesign = newTotal;
                totalFrontend -= relevantRequiredResorceUsed;
                break;
            case 3:
                totalSales = newTotal;
                totalDesign -= relevantRequiredResorceUsed;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
        //saveData(context);
        return getTwoDecimals(newTotal);
    }

    public int getTwoDecimals(float number) {
        number = number * 100;
        while (number > 100) {
            number -= 100;
        }
        return (int) number;
    }

    public boolean tooHigh(int position) {
        switch (position) {
            case 0:
                if(backendWorkers > 10){
                    return true;
                } else {
                    return false;
                }
            case 1:
                if(frontendWorkers > 10) {
                    return true;
                } else {
                    return false;
                }
            case 2:
                if(designWorkers > 10) {
                    return true;
                } else {
                    return false;
                }
            case 3:
                if(salesWorkers > 10) {
                    return true;
                } else {
                    return false;
                }
        }
        return true;
    }

    public int getWorkersByPosition (int position) {
        switch (position) {
            case 0:
                return backendWorkers;
            case 1:
                return frontendWorkers;
            case 2:
                return designWorkers;
            case 3:
                return salesWorkers;
        }
        return 1;
    }

    public void addWorker(int position, Context context) {
        switch (position) {
            case 0:
                if (backendWorkers + 1 <= backendWorkerCap) {
                    backendWorkers++;
                    saveData();
                    break;
                } else {
                    return;
                }
            case 1:
                if (frontendWorkers + 1 <= frontendWorkerCap) {
                    frontendWorkers++;
                    saveData();
                    break;
                } else {
                    return;
                }
            case 2:
                if (designWorkers + 1 <= designWorkerCap) {
                    designWorkers++;
                    saveData();
                    break;
                } else {
                    return;
                }
            case 3:
                if (salesWorkers + 1 <= salesWorkerCap) {
                    salesWorkers++;
                    saveData();
                    break;
                } else {
                    return;
                }
        }
    }

    public void takeWorker(int position, Context context) {
        switch (position) {
            case 0:
                if (backendWorkers > 0) {
                    backendWorkers--;
                    saveData();
                    break;
                } else {
                    return;
                }
            case 1:
                if (frontendWorkers > 0) {
                    frontendWorkers--;
                    saveData();
                    break;
                } else {
                    return;
                }
            case 2:
                if (designWorkers > 0) {
                    designWorkers--;
                    saveData();
                    break;
                } else {
                    return;
                }
            case 3:
                if (salesWorkers > 0) {
                    salesWorkers--;
                    saveData();
                    break;
                } else {
                    return;
                }
        }
    }

    public void saveData() {
        if(totalBackend < 0) {
            totalBackend = 0;
        }
        if(totalDesign < 0) {
            totalDesign = 0;
        }
        if(totalFrontend < 0) {
            totalFrontend = 0;
        }
        if(totalSales < 0) {
            totalSales = 0;
        }
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
        baseBackend = sharedPref.getFloat("latestBackendBase", 0);
        baseFrontend = sharedPref.getFloat("latestFrontendBase", 0);
        baseDesign = sharedPref.getFloat("latestDesignBase", 0);
        baseSales = sharedPref.getFloat("latestSalesBase", 0);
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
        backendRate = sharedPref.getFloat("backendRate", (float) 0.002);
        //backendRate = 0.5;
        frontendRate = sharedPref.getFloat("frontendRate", (float) 0.002);
        //frontendRate = 0.5;
        designRate = sharedPref.getFloat("designRate", (float) 0.002);
        salesRate = sharedPref.getFloat("salesRate", (float) 0.002);
        frontendUnlocked = sharedPref.getBoolean("frontendUnlocked", false);
        designUnlocked = sharedPref.getBoolean("designUnlocked", false);
        salesUnlocked = sharedPref.getBoolean("salesUnlocked", false);
    }

    public CharSequence getWorkersChar(int position, Context context) {
        loadData();
        switch (position){
            case 0:
                return Integer.toString(backendWorkers);
            case 1:
                return Integer.toString(frontendWorkers);
            case 2:
                return Integer.toString(designWorkers);
            case 3:
                return Integer.toString(salesWorkers);
            default:
                break;
        }
        return null;
    }

    public CharSequence getWorkerCapChar(int position, Context context) {
        loadData();
        switch (position){
            case 0:
                return Integer.toString(backendWorkerCap);
            case 1:
                return Integer.toString(frontendWorkerCap);
            case 2:
                return Integer.toString(designWorkerCap);
            case 3:
                return Integer.toString(salesWorkerCap);
            default:
                break;
        }
        return null;
    }

    public CharSequence getMaxChar(int position, Context context) {
        loadData();
        switch (position){
            case 0:
                return Integer.toString(backendCap);
            case 1:
                return Integer.toString(frontendCap);
            case 2:
                return Integer.toString(designCap);
            case 3:
                return Integer.toString(salesCap);
            default:
                break;
        }
        return null;
    }

    public void increaseMaxWorkers(int position, Context context) {
        loadData();
        switch (position) {
            case 0:
                int required = 10 * (2 ^ (backendWorkerCap - 1));
                if (totalBackend >= required) {
                    totalBackend -= required;
                    backendWorkerCap += 1;
                    saveData();
                }
                break;
            case 1:
                required = 10 * (2 ^ (frontendWorkerCap - 1));
                if (totalFrontend >= required) {
                    totalFrontend -= required;
                    frontendWorkerCap += 1;
                    saveData();
                }
                break;
            case 2:
                required = 10 * (2 ^ (designWorkerCap - 1));
                if (totalDesign >= required) {
                    totalDesign -= required;
                    designWorkerCap += 1;
                    saveData();
                }
                break;
            case 3:
                required = 10 * (2 ^ (salesWorkerCap - 1));
                if (totalSales >= required) {
                    totalSales -= required;
                    salesWorkerCap += 1;
                    saveData();
                }
                break;
        }
    }

    public int requiredForMaxWorkers(int position, Context context) {
        loadData();
        switch (position) {
            case 0:
                int required = 10 * (2 ^ (backendWorkerCap - 1));
                return required;
            case 1:
                required = 10 * (2 ^ (frontendWorkerCap - 1));
                return required;
            case 2:
                required = 10 * (2 ^ (designWorkerCap - 1));
                return required;
            case 3:
                required = 10 * (2 ^ (salesWorkerCap - 1));
                return required;
        }
        return 1;
    }

    public void increaseItemCapacity(int position, Context context) {
        loadData();
        switch (position) {
            case 0:
                double required = backendCap *.9;
                if (totalBackend >= required) {
                    totalBackend -= required;
                    backendCap += Math.round(backendCap *.2);
                    saveData();
                }
                break;
            case 1:
                required = frontendCap *.9;
                if (totalFrontend >= required) {
                    totalFrontend -= required;
                    frontendCap += Math.round(frontendCap *.2);
                    saveData();
                }
                break;
            case 2:
                required = designCap *.9;
                if (totalDesign >= required) {
                    totalDesign -= required;
                    designCap += Math.round(designCap *.2);
                    saveData();
                }
                break;
            case 3:
                required = salesCap *.9;
                if (totalSales >= required) {
                    totalSales -= required;
                    salesCap += Math.round(salesCap *.2);
                    saveData();
                }
                break;
        }
    }

    public int requiredForItemCapacity(int position, Context context) {
        loadData();
        switch (position) {
            case 0:
                double required = backendCap *.9;
                return (int) required + 1;
            case 1:
                required = frontendCap *.9;
                return (int) required + 1;
            case 2:
                required = designCap *.9;
                return (int) required + 1;
            case 3:
                required = salesCap *.9;
                return (int) required + 1;
        }
        return 1;
    }

    public void increaseRate(int position, Context context) {
        loadData();
        switch (position) {
            case 0:
                int upgrades = -1;
                double rateTracker = backendRate;
                while(rateTracker > 0) {
                    rateTracker -= baseRate/(upgrades+2);
                    upgrades++;
                }
                double required = 2000*(Math.pow(2, upgrades));
                if (totalBackend >= required) {
                    totalBackend -= required;
                    backendRate += (baseRate * Math.pow(.5,upgrades));
                    saveData();
                }
                //impliment for all three case. Then check to see if it works after adjusting down the rates. Next, impliment a way to track the rate or how much each upgrade costs.
                break;
            case 1:
                upgrades = -1;
                rateTracker = frontendRate;
                while(rateTracker > 0) {
                    rateTracker -= baseRate/(upgrades+2);
                    upgrades++;
                }
                required = 2000*(Math.pow(2, upgrades));
                if (totalFrontend >= required) {
                    totalFrontend -= required;
                    frontendRate += (baseRate * Math.pow(.5,upgrades));
                    saveData();
                }
                break;
            case 2:
                upgrades = -1;
                rateTracker = designRate;
                while(rateTracker > 0) {
                    rateTracker -= baseRate/(upgrades+2);
                    upgrades++;
                }
                required = 2000*(Math.pow(2, upgrades));
                if (totalDesign >= required) {
                    totalDesign -= required;
                    designRate += (baseRate * Math.pow(.5,upgrades));
                    saveData();
                }
                break;
            case 3:
                upgrades = -1;
                rateTracker = salesRate;
                while(rateTracker > 0) {
                    rateTracker -= baseRate/(upgrades+2);
                    upgrades++;
                }
                required = 2000*(Math.pow(2, upgrades));
                if (totalSales >= required) {
                    totalSales -= required;
                    salesRate += (baseRate * Math.pow(.5,upgrades));
                    saveData();
                }
                break;
        }
    }

    public int requiredForRate(int position, Context context) {
        loadData();
        switch (position) {
            case 0:
                int upgrades = -1;
                double rateTracker = backendRate;
                while(rateTracker > 0) {
                    //System.out.println("Rate is " + rateTracker);
                    rateTracker -= baseRate/(upgrades+2);
                    upgrades++;
                }
                //System.out.println("Upgrade count is " + upgrades);
                double required = 2000*(Math.pow(2, upgrades));
                //System.out.println("Required is " + required);
                return (int) required;
            case 1:
                upgrades = -1;
                rateTracker = frontendRate;
                while(rateTracker > 0) {
                    rateTracker -= baseRate/(upgrades+2);
                    upgrades++;
                }
                required = 2000*(Math.pow(2, upgrades));
                return (int) required;
            case 2:
                upgrades = -1;
                rateTracker = designRate;
                while(rateTracker > 0) {
                    rateTracker -= baseRate/(upgrades+2);
                    upgrades++;
                }
                required = 2000*(Math.pow(2, upgrades));
                return (int) required;
            case 3:
                upgrades = -1;
                rateTracker = salesRate;
                while(rateTracker > 0) {
                    rateTracker -= baseRate/(upgrades+2);
                    upgrades++;
                }
                required = 2000*(Math.pow(2, upgrades));
                return (int) required;
        }
        return 1;
    }

}
