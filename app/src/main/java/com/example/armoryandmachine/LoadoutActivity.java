package com.example.armoryandmachine;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class LoadoutActivity extends AppCompatActivity {

    LoadoutViewModel loadoutViewModel;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<String> toolNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadout);
        loadoutViewModel =
                new ViewModelProvider(this).get(LoadoutViewModel.class);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolNames.add("Tool 1");
        toolNames.add("Tool 2");
        toolNames.add("Tool 3");
        recyclerView =  findViewById(R.id.loadoutRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecyclerViewAdapterLoadout(this, toolNames, this);
        recyclerView.setAdapter(mAdapter);
    }

    public void updateLoadout() {
        Button slot1 = findViewById(R.id.slot1);
        if(loadoutViewModel.tool1.getName().equals("default")) {

        } else {
            slot1.setText(loadoutViewModel.tool1.getName());
        }
        Button slot2 = findViewById(R.id.slot2);
        if(loadoutViewModel.tool2.getName().equals("default")) {

        } else {
            slot2.setText(loadoutViewModel.tool2.getName());
        }
        Button slot3 = findViewById(R.id.slot3);
        if(loadoutViewModel.tool3.getName().equals("default")) {

        } else {
            slot3.setText(loadoutViewModel.tool3.getName());
        }
        Button slot4 = findViewById(R.id.slot4);
        if(loadoutViewModel.tool4.getName().equals("default")) {

        } else {
            slot4.setText(loadoutViewModel.tool4.getName());
        }
    }
}
