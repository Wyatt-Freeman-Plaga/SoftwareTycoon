package com.example.armoryandmachine.ui.research;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.armoryandmachine.MainViewModel;
import com.example.armoryandmachine.R;
import com.example.armoryandmachine.RecyclerViewAdapterResearch;

import java.util.ArrayList;

public class ResearchFragment extends Fragment {

    private ResearchViewModel researchViewModel;
    public MainViewModel mainViewModel;
    private ArrayList<String> mResource = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        researchViewModel =
                ViewModelProviders.of(this).get(ResearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_research, container, false);
        //final TextView textView = root.findViewById(R.id.text_research);
        researchViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        initImageBitmaps();
        initRecyclerView(root);
        return root;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        mainViewModel = new ViewModelProvider(super.getActivity()).get(MainViewModel.class);
    }

    private void initImageBitmaps(){
        mResource.add("Backend");

        mainViewModel.loadData();

        if(mainViewModel.frontendUnlocked == true) {
            mResource.add("Frontend");
        }

        if(mainViewModel.designUnlocked == true) {
            mResource.add("Design");
        }

        if(mainViewModel.salesUnlocked == true) {
            mResource.add("Sales");
        }
    }

    private void initRecyclerView(View root){
        RecyclerView recyclerView =  root.findViewById(R.id.recycler_view);
        if(mainViewModel.researchAdapter == null) {
            RecyclerViewAdapterResearch adapter = new RecyclerViewAdapterResearch(super.getContext(), mResource, this);
            recyclerView.setAdapter(adapter);
            System.out.println("New adapter made.");
        } else {
            recyclerView.setAdapter(mainViewModel.researchAdapter);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(super.getContext()));
    }

    /*public void upgradeWorkerCapactiy(int position, TextView capacityViewToUpgrade, Context context) {
        switch (position) {
            case 0:
                if (heatWorkers + 1 <= heatWorkerCap) {
                    heatWorkers++;
                    saveBase(context);
                    break;
                } else {
                    return;
                }
            case 1:
                if (tankWorkers + 1 <= tankWorkerCap) {
                    tankWorkers++;
                    saveBase(context);
                    break;
                } else {
                    return;
                }
            case 2:
                if (kilnWorkers + 1 <= kilnWorkerCap) {
                    kilnWorkers++;
                    saveBase(context);
                    break;
                } else {
                    return;
                }
            case 3:
                if (fluidWorkers + 1 <= fluidWorkerCap) {
                    fluidWorkers++;
                    saveBase(context);
                    break;
                } else {
                    return;
                }
        }
    }*/



    public void upgradeWorkerCapacityRequest(int position, TextView capacityViewToUpgrade, Context context) {
        //researchViewModel.upgradeWorkerCapacity(position, context);
        //capacityViewToUpgrade.setText(researchViewModel.getWorkersChar(position, context));
    }
}