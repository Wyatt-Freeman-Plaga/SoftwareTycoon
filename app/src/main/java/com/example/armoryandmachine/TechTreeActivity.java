package com.example.armoryandmachine;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.util.Calendar;

public class TechTreeActivity extends AppCompatActivity {

    TechTreeViewModel techTreeViewModel;
    static public boolean active = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("input started");
        setContentView(R.layout.activity_tech);
        techTreeViewModel =
                new ViewModelProvider(this).get(TechTreeViewModel.class);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView backend = findViewById(R.id.Backend);
        backend.setTextColor(Color.BLACK);
        System.out.println("HEY");
        techTreeViewModel.loadData();
        if(techTreeViewModel.frontendUnlocked == true) {
            TextView text = findViewById(R.id.Frontend);
            text.setTextColor(Color.BLACK);
            text.setText("Frontend unlocked");
            text = findViewById(R.id.neededForFrontend);
            text.setVisibility(View.GONE);
        }
        if(techTreeViewModel.designUnlocked == true) {
            TextView text = findViewById(R.id.Design);
            text.setTextColor(Color.BLACK);
            text.setText("Design unlocked");
            text = findViewById(R.id.neededForDesign);
            text.setVisibility(View.GONE);
        }
        if(techTreeViewModel.salesUnlocked == true) {
            TextView text = findViewById(R.id.Sales);
            text.setTextColor(Color.BLACK);
            text.setText("Sales unlocked");
            text = findViewById(R.id.neededForSales);
            text.setVisibility(View.GONE);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(1>0){
                    new Handler(Looper.getMainLooper()).post(new Runnable(){
                        @Override
                        public void run() {
                            TextView text = findViewById(R.id.totalBackend);
                            techTreeViewModel.loadData();
                            text.setText("Backend: " + String.valueOf((int) techTreeViewModel.totalBackend));
                            text = findViewById(R.id.totalFrontend);
                            text.setText("Frontend: " + String.valueOf((int) techTreeViewModel.totalFrontend));
                            text = findViewById(R.id.totalDesign);
                            text.setText("Design: " + String.valueOf((int) techTreeViewModel.totalDesign));
                            text = findViewById(R.id.totalSales);
                            text.setText("Sales: " + String.valueOf((int) techTreeViewModel.totalSales));
                        }
                    });
                    android.os.SystemClock.sleep(500);
                }
            }
        }).start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        active = false;
    }

    public void unlockFrontendRequest(View v) {
        if(techTreeViewModel.unlockFrontend(this.getApplicationContext()) == true) {
            TextView text = findViewById(R.id.Frontend);
            text.setTextColor(Color.BLACK);
            text.setText("Frontend unlocked");
            text = findViewById(R.id.neededForFrontend);
            text.setText("");
        }
    }

    public void unlockDesignRequest(View v) {
        if(techTreeViewModel.unlockDesign(this.getApplicationContext()) == true) {
            TextView text = findViewById(R.id.Design);
            text.setTextColor(Color.BLACK);
            text.setText("Design unlocked");
            text = findViewById(R.id.neededForDesign);
            text.setText("");
        }
    }

    public void unlockSalesRequest(View v) {
        if(techTreeViewModel.unlockSales(this.getApplicationContext()) == true) {
            TextView text = findViewById(R.id.Sales);
            text.setTextColor(Color.BLACK);
            text.setText("Sales unlocked");
            text = findViewById(R.id.neededForSales);
            text.setText("");
        }
    }
}
