package com.example.armoryandmachine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.armoryandmachine.ui.craft.CraftFragment;
import com.example.armoryandmachine.ui.craft.CraftViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainViewModel =
                new ViewModelProvider(this).get(MainViewModel.class);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_craft, R.id.navigation_research, R.id.navigation_log, R.id.navigation_jobs)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences("master", Context.MODE_PRIVATE);
        System.out.println(sharedPref.getString("companyName", "hello"));
        if(sharedPref.getString("companyName", null) == null) {
            System.out.println("Starting input");
            startCompanyNameInput();
        }
    }

    public void startTechTree(View v) {
        Intent intent = new Intent(this, TechTreeActivity.class);
        startActivity(intent);
    }

    /*public void startLoadout(View v) {
        Intent intent = new Intent(this, LoadoutActivity.class);
        startActivity(intent);
    }*/

    public void startCompanyNameInput() {
        Intent intent = new Intent(this, StartCompanyNameActivity.class);
        startActivity(intent);
    }

    public void startLoadout(View view) {
        Intent intent = new Intent(this, LoadoutActivity.class);
        startActivity(intent);
    }
}